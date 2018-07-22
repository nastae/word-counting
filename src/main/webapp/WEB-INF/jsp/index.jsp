<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Word counting</title>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;    
}
</style>
</head>
<body>
    <div id="global">
        <form:form modelAttribute="files" action="/" method="post" enctype="multipart/form-data">
            <fieldset>
                <p>
                    <label for="file">Files: </label>
                    <input type="file" name="files" multiple accept=".pdf, .odt, .ods, .odp, .doc, .docx, .xls, .txt, .html, .xml, .epub"/>
                </p>
                <p id="buttons">
                    <input id="reset" type="reset" tabindex="4">
                    <input id="submit" type="submit" tabindex="5" value="Convert">
                </p>
            </fieldset>
        </form:form>
    </div>
    </br>
    
    <c:if test="${!empty words}">	
	    <fieldset>
		    <h4>Words starting with A-G</h4>
		    <table>
		        <c:forEach items="${words}" var="word">
		           	<c:choose>
			   			<c:when test="${word.type == '0'}">
			   				<tr>
			   					<th>${word.word}</th>
			   				 	<th>${word.count}</th>
			   				</tr>
			   			</c:when> 
					</c:choose>
		        </c:forEach>
			</table>
		
		    <h4>Words starting with H-N</h4>
		    <table>
		        <c:forEach items="${words}" var="word">
		           	<c:choose>
			   			<c:when test="${word.type == '1'}">
			   				<tr>
			   					<th>${word.word}</th>
			   				 	<th>${word.count}</th>
			   				</tr>
			   			</c:when> 
					</c:choose>
		        </c:forEach>
			</table>
			
		    <h4>Words starting with O-U</h4>
		    <table>
		        <c:forEach items="${words}" var="word">
		           	<c:choose>
			   			<c:when test="${word.type == '2'}">
			   				<tr>
			   					<th>${word.word}</th>
			   				 	<th>${word.count}</th>
			   				</tr>
			   			</c:when> 
					</c:choose>
		        </c:forEach>
			</table>
			
		    <h4>Words starting with V-Z</h4>
		    <table>
		        <c:forEach items="${words}" var="word">
		           	<c:choose>
			   			<c:when test="${word.type == '3'}">
			   				<tr>
			   					<th>${word.word}</th>
			   				 	<th>${word.count}</th>
			   				</tr>
			   			</c:when> 
					</c:choose>
		        </c:forEach>
			</table>
	    </fieldset>
    </c:if>
</body>
</html>