<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-boarder">
						<h3 class="box-title">전부다 나와라~~</h3>
					</div>
					<div class="box-body">
						<form method="post" action="modify${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${board.bno}">
							<div class="form-group">
								<label>Title</label>
								<input type="text" name="title" class="form-control" placeholder="title" value="${board.title}">
							</div>
							<div class="form-group">
								<label>Content</label>
								<textarea rows="5" class="form-control" placeholder="content" name="content">${board.content}</textarea>
							</div>
							<div class="form-group">
								<label>Writer</label>
								<input type="text" name="writer" class="form-control" placeholder="writer" value="${board.writer}">
							</div>
							<div class="form-group" id="image_wrap">
							<c:forEach var="file" items="${board.files}">
								<div class="imgWrapper">
									<img src="displayFile?filename=${file}">
									<input type="button" value="X" class="delImg">
								</div>
								
							</c:forEach>
						</div>
							<div class="form-group">
								<input type="submit" value="OK" class="btn btn-warning">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

<%@ include file="../include/footer.jsp" %>