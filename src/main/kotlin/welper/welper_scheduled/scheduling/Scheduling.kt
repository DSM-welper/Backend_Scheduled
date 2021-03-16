package welper.welper_scheduled.scheduling

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import welper.welper_scheduled.repository.OpenAPIFieldRepository
import welper.welper_scheduled.repository.OpenAPIRepository
import welper.welper_scheduled.domain.OpenAPI
import welper.welper_scheduled.domain.OpenAPIField
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Component
class Scheduling(
        private val openAPIRepository: OpenAPIRepository,
        private val openAPIFieldRepository: OpenAPIFieldRepository,
) {

    @Scheduled(fixedRate = 18000000)
    fun createServList() {
        val list: MutableList<Document> = mutableListOf()
        val docList: MutableList<Document> = categoryURL(1, list)
        val lifeArray: List<String> = listOf("infants", "child", "teenAge", "youth", "middleAge", "oldAge")
        val charTrgterArray: List<String> = listOf("doNot", "women", "pregnant", "disorder", "nationalMerit", "unemployed")
        val trgterIndvdlArray: List<String> = listOf("doNot", "singleparents", "multiculture", "grandchildren", "settermin", "childHead", "soloOld")
        val desireArray: List<String> = listOf("safety", "health", "dailylife", "family", "social", "economic", "education", "employment", "life", "Law")
        for (i in 0..5) {
            val lifeArrayList: MutableList<Document> = getLifeArray(1, list, "00${i + 1}")
            getCategory(lifeArrayList, "lifeArray", lifeArray[i])
        }
        for (i in 0..5) {
            val charTrgterArrayList: MutableList<Document> = getCharTrgterArray(1, list, "00${i + 1}")
            getCategory(charTrgterArrayList, "charTrgterArray", charTrgterArray[i])
        }
        for (i in 0..6) {
            val trgterIndvdlArrayList: MutableList<Document> = getTrgterIndvdlArray(1, list, "00${i + 1}")
            getCategory(trgterIndvdlArrayList, "trgterIndvdlArray", trgterIndvdlArray[i])
        }
        for (i in 0..9) {
            val desireArrayList: MutableList<Document> = getDesireArray(1, list, "${i}000000")
            getCategory(desireArrayList, "desireArray", desireArray[i])
        }
        val obstAbtArray10: MutableList<Document> = getObstAbtArray(1, list, "10")
        val obstAbtArray20: MutableList<Document> = getObstAbtArray(1, list, "20")
        val desireArrayExcept: MutableList<Document> = getDesireArray(1, list, "A000000")
        getCategory(obstAbtArray10, "obstAbtArray", "severe")
        getCategory(obstAbtArray20, "obstAbtArray", "weak")
        getCategory(desireArrayExcept, "desireArray", "except")

        getAllCategory(docList)
    }

    private fun getAllCategory(docList: MutableList<Document>) {
        println("모두 저장")
        docList.forEach {
            val nList: NodeList = it.getElementsByTagName("servList");

            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id: String = getTagValue("servId", eElement)
                if (!openAPIRepository.existsById(id))
                    openAPIRepository.save(OpenAPI(

                            inqNUm = getTagValue("inqNum", eElement),
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

    private fun getCategory(docList: MutableList<Document>, fieldName: String, fieldContent: String) {
        docList.forEach {
            println("필드 저장")
            val nList: NodeList = it.getElementsByTagName("servList");

            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id: String = getTagValue("servId", eElement) ?: ""
                if (openAPIFieldRepository.existsByApiIdAndFieldContent(id, fieldContent) == false) {
                    openAPIFieldRepository.save(OpenAPIField(
                            apiId = getTagValue("servId", eElement),
                            fieldName = fieldName,
                            fieldContent = fieldContent,
                    )
                    )
                }
            }
        }
    }


    private fun getTagValue(tag: String, eElement: Element): String {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }

    private fun categoryURL(
            num: Int,
            list: MutableList<Document>,
    )

            : MutableList<Document> {
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
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getLifeArray(
            num: Int,
            list: MutableList<Document>,
            lifeArray: String,
    )
            : MutableList<Document> {
        println("getlife 읽어오기")

        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&lifeArray=$lifeArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getCharTrgterArray(
            num: Int,
            list: MutableList<Document>,
            charTrgterArray: String,
    )
            : MutableList<Document> {
        println("getCharTrgter 읽어오기")

        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&charTrgterArray=$charTrgterArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getObstAbtArray(
            num: Int,
            list: MutableList<Document>,
            obstAbtArray: String,
    )
            : MutableList<Document> {
        println("getObstAbt 읽어오기")

        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&charTrgterArray=004" +
                "&numOfRows=100&obstAbtArray=$obstAbtArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getTrgterIndvdlArray(
            num: Int,
            list: MutableList<Document>,
            trgterIndvdlArray: String,
    )
            : MutableList<Document> {
        println("getTrgterIndvdl 읽어오기")

        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&trgterIndvdlArray=$trgterIndvdlArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getDesireArray(
            num: Int,
            list: MutableList<Document>,
            desireArray: String,
    )
            : MutableList<Document> {
        println("getDesire 읽어오기")
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&desireArray=$desireArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }
}