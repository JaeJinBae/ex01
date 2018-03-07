<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>


	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-boarder">
						<h3 class="box-title">전부다 나와라~~</h3>
					</div>
					<div class="box-body">
						<form method="get" action="">
							<input type="hidden" name="bno" value="${board.bno }">
						</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="title" value="${board.title}" readonly="readonly">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" class="form-control" placeholder="content" name="content" readonly="readonly">${board.content}</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="writer" value="${board.writer}" readonly="readonly">
						</div>
						<div class="form-group">
							<a href="modify?bno=${board.bno}"><input type="button" value="modify" class="btn btn-warning"></a>
							<a href="remove?bno=${board.bno}"><input type="button" value="remove" class="btn btn-danger"></a>
							<a href="listAll"><input type="button" value="Go list" class="btn btn-primary"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

<%@ include file="../include/footer.jsp" %>