package welper.welper_scheduled.scheduling

import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper_scheduled.attribute.Category
import welper.welper_scheduled.domain.OpenApICategory
import welper.welper_scheduled.domain.OpenApiPost

import welper.welper_scheduled.repository.OpenApiCategoryRepository

import welper.welper_scheduled.repository.OpenApiPostRepository
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Component
class Scheduling(
        private val openApiCategoryRepository: OpenApiCategoryRepository,
        private val openApiPostRepository: OpenApiPostRepository,
) {

    @Scheduled(fixedRate = 18000000)
    fun createServList() {
        var list: MutableList<Document> = mutableListOf()
        val docList: MutableList<Document> = readPost(1, list)
        saveAllCategory(docList)

        Category.values().forEach {
            list = mutableListOf()
            val lifeArrayList: MutableList<Document> = readCategory(1, list, it.code)
            saveCategory(lifeArrayList, it.value)
        }
    }

    private fun saveAllCategory(docList: MutableList<Document>) {
        println("모두 저장")
        docList.forEach {
            val nList: NodeList = it.getElementsByTagName("servList")

            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id: String = getTagValue("servId", eElement)
                if (!openApiPostRepository.existsById(id)) {
                    openApiPostRepository.save(
                            OpenApiPost(
                                    inqNum = getTagValue("inqNum", eElement),
                                    jurOrgNm = getTagValue("jurOrgNm", eElement),
                                    servDgst = getTagValue("servDgst", eElement),
                                    servDtlLink = getTagValue("servDtlLink", eElement),
                                    servId = getTagValue("servId", eElement),
                                    servNm = getTagValue("servNm", eElement),
                                    svcfrstRegTs = getTagValue("svcfrstRegTs", eElement),
                                    jurMnofNm = getTagValue("jurMnofNm", eElement),
                            )
                    )
                }
            }
        }
    }

    private fun saveCategory(docList: MutableList<Document>, categoryName: String) {

        docList.forEach {
            val nList: NodeList = it.getElementsByTagName("servList");
            println(nList.length)
            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id: String = getTagValue("servId", eElement)
                val openApiPost: OpenApiPost = openApiPostRepository.findByIdOrNull(id) ?: throw Exception()
                if (!openApiCategoryRepository.existsByCategoryNameAndOpenApiPost(categoryName, openApiPost))
                    openApiCategoryRepository.save(
                            OpenApICategory(
                                    categoryName = categoryName,
                                    openApiPost = openApiPost
                            )
                    )
            }
        }
    }

    private fun readPost(
            num: Int,
            list: MutableList<Document>,
    ): MutableList<Document> {
        println("모두읽어오기")
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        println(doc.getElementsByTagName("servList").length)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            readPost(num2, list)
        }
        list.add(doc)
        return list
    }

    private fun readCategory(
            num: Int,
            list: MutableList<Document>,
            categoryName: String,
    ): MutableList<Document> {
        println("category 읽어오기")
        var num2 = num
        println(num2)
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100$categoryName"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            readCategory(num2, list, categoryName)
        }
        list.add(doc)

        return list
    }

    private fun getTagValue(tag: String, eElement: Element): String {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }
}