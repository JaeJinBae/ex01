<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<style>
	th{
		text-align: center;
	}
</style>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-boarder">
						<h3 class="box-title">전부다 나와라~~</h3>
					</div>
					<div class="box-body">
						<select name="searchType">
							<option value="n">선택해주세요.</option>
							<option value="t" ${cri.searchType=='t'?'selected':''}>Title</option>
							<option value="c" ${cri.searchType=='c'?'selected':''}>Content</option>
							<option value="w" ${cri.searchType=='w'?'selected':''}>Writer</option>
							<option value="tc" ${cri.searchType=='tc'?'selected':''}>Title or Content</option>
							<option value="cw" ${cri.searchType=='cw'?'selected':''}>Content or Writer</option>
							<option value="tcw" ${cri.searchType=='tcw'?'selected':''}>Title or Content or Writer</option>
						</select> 
						<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}">
						<button id="searchBtn">Search</button>
						<button id="newBtn">New Board</button> 
					</div>
					<div class="box-body">
						<table class="table table-bordered">
							<thead> 
								<tr>
									<th>BNO</th>
									<th>TITLE</th>
									<th>WRITER</th>
									<th>REGDATE</th>
									<th>VIEW_COUNT</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="board" items="${list}">
									<tr>
										<td style="width:10px;">${board.bno}</td>
										<td><a href="readPage${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${board.bno}">${board.title}</a></td>
										<td>${board.writer}</td>
										<td>
											<fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm"/> 
										</td>
										<td style="width:40px;">
											<span class="badge bg-red">${board.viewcnt}</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="box-footer">
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pageMaker.prev}">
									<li><a href="listPage${pageMaker.makeSearch(pageMaker.startPage-1) }">&laquo;</a></li>
								</c:if>
								
								<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
									<li ${pageMaker.cri.page == idx? 'class=active':''}><a href="listPage${pageMaker.makeSearch(idx)}">${idx}</a></li>
								</c:forEach>
								
								<c:if test="${pageMaker.next}">
									<li><a href="listPage${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
<script>
$(function(){
	$("#searchBtn").click(function(){
		var searchType=$("select[name='searchType']").val();
		var keyword=$("input[name='keyword']").val();
		location.href="listPage${pageMaker.makeQuery(1)}&searchType="+searchType+"&keyword="+keyword;
	});
	$("#newBtn").click(function(){
		location.href="register";
		
	});
});
</script>

<%@ include file="../include/footer.jsp" %>