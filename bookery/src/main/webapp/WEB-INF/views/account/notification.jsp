<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Bookery</title>
	<%@ include file="../template/head.jspf" %>
	<script type="text/javascript">
	
//	var reply_noti_list = "replyMyPost";
	$(function(){
		aos();
		
		/* 게시글 박스 클릭 시 Detail로 이동 */
		$('.panel-post').each(function(idx,ele){
			var aHref = $(this).find('a').attr('href');
			var club_id = $(this).find('#id').val();
			
			$(this).on('click',function(){
				location.href=aHref;
				
				/* 확인한 답변으로 바꾼다. title에 값 업데이트 */
				$.ajax('${pageContext.request.contextPath}/account/noti/check',{
					method:'post',
					data:'id='+club_id,
					success:function(){
					},error:function(){
					}
				});//ajax
			});//click 
		});//each
		
		/* 제일 위로 */
		$('.btn-top').click(function(){
			$(document).scrollTop(0);			
		});//top버튼 클릭
		
		/* 검색결과가 10개미만이면 더보기 버튼 숨김 */
		var cnt_posts=0;
		$('.panel-post').each(function(){
			cnt_posts++;
		});//each
		if(cnt_posts<9 && cnt_posts>0){
			$('.more-posts').hide();
		}else if(cnt_posts == 0){   //게시물이 1건도 없을 때 기본으로 공지사항 1개 생성.
			$('.more-posts').hide();
			var post = '';
				post += '<div class="panel panel-default panel-post">'
				post += '<div class="panel-body"><span class="lead">새로운 알림이 없습니다.</span></div>'
				post += '<div class="panel-body"><span class="user_id"> bookery </span>';
				post += '&nbsp;|&nbsp;<span class="wrote-day">공지사항</span></div>';
				post += '</div><br/>';
				$('.div-post').append(post);
		}	
	});//ready
	
	</script>
	<style type="text/css">
	
.panel-post { /*   */
	border: 1px solid rgb(221, 221, 221);
	box-shadow: 0 2px 8px rgba(0,0,0,.1), 0 8px 20px rgba(0,0,0,.1);
	border-radius: 16px;
	transition-duration: 600ms;
	display: block;
	margin: auto;
	margin-top: 3px;
	margin-bottom:30px;
}
.panel-post:hover { /*  반짝 */
	transition-duration: 600ms;
	border: 1px solid rgb(139, 169, 137);
	box-shadow: rgb(192, 207, 178) 0px 0px 6px;
	cursor: pointer;
}
	
	
	</style>
	</head>
<body>
<%@ include file="../template/menu.jspf" %>
<!-- **********content start********** -->.

<div class="row">
<div class="col-md-2"></div>
<div class="col-xs-12 col-md-8 bottom-line">

	<h3>  <span class="glyphicon glyphicon-bell" aria-hidden="true"></span>&nbsp;&nbsp;${notiSize}건의 댓글이 있습니다.</h3>
	<p>댓글을 클릭하면 게시글로 바로 이동할 수 있습니다.</p>
	
</div>
<div class="col-md-2"></div>
</div>
	<!--
	
	내가 작성한글 club id >>  depth=1 ref= 내글id       달린 댓글들을 불러옴. 
	
	클릭하면 댓글 title에 1을 넣고. title이 1인건 안띄우기. 
	  -->
	<div class="row">
	<div class="col-md-2"></div>
	<div class="col-xs-12 col-md-8  div-post">
	
		<c:forEach items="${replyMyPost }" var="bean" begin="0" end="29">
				<div class="panel panel-default panel-post" data-aos="fade-up">
					<input type="hidden" name="id" id="id" value="${bean.id }"/>
					<div class="panel-body">
					<p>	<strong><span class="o-titl">${bean.o_titl }</span></strong>&nbsp;&nbsp;<small>에 달린 댓글</small></p>
					<a href="${pageContext.request.contextPath }/club/detail/${bean.ref}"><span class="">${bean.content }</span></a></div>
					<div class="panel-body"><span class="user_id">${bean.nickname }</span> &nbsp;|&nbsp;<span class="update-day">${bean.updatetime }</span>
					</div>
				</div>
		</c:forEach>
	
	</div>
	</div>



	<div class="row">
	<div class="col-md-2"></div>
	<div class="col-xs-12 col-md-8">
				<br/>
				<br/>
				<button class="btn btn-default more-posts">더보기</button>
				<button class="btn btn-default btn-top">Top</button>
	</div>
	</div>


<!-- **********content end********** --> 
<%@ include file="../template/footer.jspf" %>
</body>
</html>