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
						<%-- <form method="get" action="">
							<input type="hidden" name="bno" value="${board.bno}">
							<input type="hidden" name="page" value="${cri.page}">
							<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
						</form> --%>
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
						<div class="form-group" id="image_wrap">
							<c:forEach var="file" items="${board.files}">
								<img src="displayFile?filename=${file}">
							</c:forEach>
						</div>
						<div class="form-group">
						<c:if test="${board.writer == login.userid}">
							<a href="modify${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${board.bno}"><input type="button" value="modify" class="btn btn-warning"></a>
							<a href="remove?bno=${board.bno}&page=${cri.page}&perPageNum=${cri.perPageNum}"><input type="button" value="remove" class="btn btn-danger"></a>
						</c:if>
							<a href="listPage${pageMaker.makeSearch(pageMaker.cri.page)}"><input type="button" value="Go list" class="btn btn-primary"></a>
						</div>
					</div>
				</div>
			</div> 
		</div>
		
		<!-- replies[ -->
		<div class="row">
			<div class="col-md-12">
				<div class="box box-success">
					<div class="box-header">
						<h3 class="box-title">Add Reply</h3>
					</div>
					<div class="box-body">
						<label for="exampleInputEmail1">Writer</label>
						<input type="text" id="writer" class="form-control" value="${login.userid}" readonly="readonly">
						<label for="replytext">Reply Text</label>
						<input type="text" id="replytext" class="form-control">
					</div>
					<div class="box-footer">
						<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
					</div>
				</div>
				<ul class="timeline">
					<li class="time-label" id="repliesDiv">
						<span class="bg-green">Replies List[${board.replycnt}]</span>
					</li>
				</ul>
				<div class="text-center">
					<ul id="pagination" class="pagination pagination-sm no-margin">
					</ul>
					<!-- modal -->
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog modal-lg">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title">Modal Header</h4>
					        </div>
					        <div class="modal-body">
					          <input type="text" id="modalContent">
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal" id="modifyReply">Modify</button>
					          <button type="button" class="btn btn-default" data-dismiss="modal" id="deleteReply">Delete</button>
					          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					        </div>
					      </div>
					    </div>
					  </div>
					<!--  -->
				</div>
			</div>
		</div>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
		<script id="template" type="text/x-handlebars-template">
			{{#each.}}
				<li class="replyLi" data-rno={{rno}}>
					<i class="fa fa-comments bg-blue"></i>
					<div class="timeline-item">
						<span class="time">
							<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
						</span>
						<h3 class="timeline-header"><strong class='modalRno'>{{rno}}</strong>-{{replyer}}</h3>
						<div class="timeline-body"><span class='modalText'>{{replytext}}</span></div>
						
						{{#if replyer}}
						<div class="timeline-footer">
							<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal">Modify</a>
						</div>
						{{/if}}
					</div>
				</li>
			{{/each}}
		</script>
		<script>
			Handlebars.registerHelper("if",function(replyer, options){
				if(replyer == "${login.userid}"){
					return options.fn(this);
				}else{
					return "";
				}
			});
		
			Handlebars.registerHelper("prettifyDate",function(value){
				var dateObj= new Date(value);
				var year=dateObj.getFullYear();
				var month=dateObj.getMonth()+1;
				var date=dateObj.getDate();
				
				return year+"/"+month+"/"+date;
			});
		
			var bno=${board.bno};
			var replyPage=1;
			var modifyRno=0;
			var templateFunc=Handlebars.compile($("#template").html());
			
			$(function(){
				$("#replyAddBtn").click(function(){
					
					var replyer=$("#writer").val();
					var replytext=$("#replytext").val();
					
					var sendData={bno:bno, replyer:replyer, replytext:replytext};
					
					$.ajax({
						url:"${pageContext.request.contextPath}/replies/",
						type:"post",
						headers:{"Content-Type":"application/json"},
						dataType:"text",
						data:JSON.stringify(sendData),//json객체를 json string 으로 변경해줌
						success:function(result){
							console.log(result);
							getPage(replyPage);
							alert(result);
						}
					})
				});
				
				function getPage(page){
					$.ajax({
						url:"${pageContext.request.contextPath}/replies/"+bno+"/"+page,
						type:"get",
						dataType:"json",
						success:function(json){
							console.log(json);					
							$(".replyLi").remove();
							
							$(".timeline").append(templateFunc(json.list));
							printPaging(json.pageMaker);
						}
					});				
				}
				
				function printPaging(pageMaker){
					var str="";
					if(pageMaker.prev){
						str+="<li><a href=''"+(pageMaker.startPage-1)+"> << </a></li>"
					}
					
					for(var i=pageMaker.startPage;i<=pageMaker.endPage;i++){
						if(pageMaker.cri.page==i){
							str+="<li class='active'><a href='"+i+"'>"+i+"</a></li>";
						}else{
							str+="<li><a href='"+i+"'>"+i+"</a></li>";
						}
					}
					
					if(pageMaker.next){
						str+="<li><a href='"+(pageMaker.endPage+1)+"'> >> </a></li>"
					}
					$("#pagination").html(str);
				}
				
				$("#repliesDiv").click(function(){
					getPage(1);
				});
				
				$(document).on("click","#pagination li a", function(){
					//e.pre
					alert($(this).text());
					getPage($(this).text());
				});
				
				
				$(document).on("click",".replyLi .timeline-item .timeline-footer a",function(){
					modifyRno=$(this).parents(".timeline-item").find(".modalRno").text();
					var reText=$(this).parents(".timeline-item").find(".modalText").text();
					
					$("#modalContent").val(reText); 
				});
				
				$(document).on("click","#modifyReply",function(){
					var modifytext=$("#modalContent").val();
					var sendData={replytext:modifytext};
					
					$.ajax({
						url:"${pageContext.request.contextPath}/replies/"+modifyRno,
						type:"put",
						dataType:"text",
						data:JSON.stringify(sendData),
						headers:{"Content-Type":"application/json"},
						success:function(result){
							console.log(result);
							alert("수정 성공");
							getPage(1);
						}
					})
				});
				
				$(document).on("click","#deleteReply",function(){
					
					$.ajax({
						url:"${pageContext.request.contextPath}/replies/"+modifyRno,
						type:"delete",
						success:function(result){
							console.log(result);
							alert("삭제완료");
							getPage(1);
						}
						
					});
				});
				
			});
			
			
		</script>
		<!-- ]replies -->
	</section>

<%@ include file="../include/footer.jsp" %>