		$("span.menu").click(function() {
			$(".top-menu ul").slideToggle("slow", function() {
			});
		});

		$(document).ready(function() {
			$().UItoTop({
				easingType : 'easeOutQuart'
			});
			$(".smallnav").click(function() {
				var smallnav = $(this);
				if (!smallnav.hasClass('active')) {
					$(".smallnav").removeClass('active');
					smallnav.addClass('active');
					var targetContent = smallnav.attr("targetContent");
					if (targetContent == 'content') {
						$(".contact").css("display", "none");
						$(".content").css("display", "block");
					} else if (targetContent == 'contact') {
						$(".content").css("display", "none");
						$(".contact").css("display", "block");
					}
				}
			});
			$(".bignav").click(function() {
				var smallnav = $(this);
				if (!smallnav.hasClass('active')) {
					$(".bignav").removeClass('active');
					smallnav.addClass('active');
					var targetContent = smallnav.attr("targetContent");
					if (targetContent == 'blog') {
						$(".mainPage").css("display", "none");
						$(".blog").css("display", "block");
					} else if (targetContent == 'mainPage') {
						$(".blog").css("display", "none");
						$(".mainPage").css("display", "block");
					}
				}
			});
			$.ajax({
				type : 'GET',
				url : '/visitReport',
				success : function(data) {
					$(".visitReport").html("来访报道(访问量:" + data + ")");
				},
				error : function() {

				}
			});
			$(".visitReport").click(function() {
				$.ajax({
					type : 'POST',
					url : '/visitReport',
					success : function(data) {
						if (data == 'repeat') {
							alert("知道您已经来访过了，请不要重复报道");
						} else {
							alert("感谢您的来访");
							$(".visitReport").html("来访报道(访问量:" + data + ")");
						}
					},
					error : function() {
						alert("不好意思，服务器出现异常了");
					}
				});
			});
			
			$(".leaveMessage input[type='submit']").click(function(){
				var baseMessage = $(".leaveMessage input[type='text']");	
				var name=baseMessage[0].value;
				var email=baseMessage[1].value;
				var qq=baseMessage[2].value;
				var leaveMessage = $(".leaveMessage textarea")[0];
				var message = leaveMessage.value;	
				$.ajax({
					type : 'POST',
					url : '/leaveMessage',
					data: {
						'name':name,
						'email':email,
						'qq':qq,
						'message':message
					},
					dataType:"text",
					success : function(data) {
						if (data == 'success') {
							baseMessage[0].value="";
							baseMessage[1].value="";
							baseMessage[2].value="";
							leaveMessage.value="";
							alert("感谢您的留言");
						} else {
							alert(data);
						}
					},
					error : function() {
						alert("不好意思，服务器出现异常了");
					}
				});
			});
			
			$.ajax({
				type : 'GET',
				url : '/blogTitles',
				success : function(data) {
					var blogTitleList = $.parseJSON(data);
					var titles = "";
					var mainTitles = "";
					$.each(blogTitleList, function(n, blog) {
						var blogId;
						var blogTitle;
						var blogAbstract;
						var writeTime;// 没用到暂时
						$.each(blog, function(key, value) {
							if (key == "blogId") {
								blogId = value;
							} else if (key == "blogTitle") {
								blogTitle = value;
							} else if (key == "blogAbstract") {
								blogAbstract = value;
							} else if (key == "writeTime") {
								writeTime = value;
							}
						});
						titles += '<div class="term"><h4 class="title" blogId="'+blogId+'">'+(n+1)+")"+blogTitle+"</h4><p>"+blogAbstract+"</p></div>";
						if(n<3){
							mainTitles = "<li><a "+"blogId='"+blogId+"'>"+blogTitle+"</a></li>";
						}
//						<li><a>博文名1</a></li>
//						<li><a >博文名2</a></li>
//						<li><a >博文名3</a></li>
					});
					$(".terms-sec-head").after(titles);	
					$(".categories h3").after(mainTitles);
				},
				error : function() {
				}
			});
		});