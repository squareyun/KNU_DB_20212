$(document).ready(function() {

	$("#chat-circle").click(function() {
		$("#chat-circle").toggle('scale');
		$("#chatbox").toggle('scale');
	})

	$(".chat-box-toggle").click(function() {
		$("#chat-circle").toggle('scale');
		$("#chatbox").toggle('scale');
	})

	$("#createChatroom input").focus(function() {
		if ($(this).val() == "input chatroomname") {
			$(this).val("");
		}
		$("#createChatroom button").addClass("show");
	});
	$("#createChatroom input").focusout(function() {
		if ($(this).val() == "") {
			$(this).val("input chatroomname");
			$("#createChatroom button").removeClass("show");
		}
	});

	$(".chatroom").each(function() {
		$(this).click(function(e) {
			if (!$(e.target).hasClass("chatroomimg")) {
				$("#chat-messages").empty();
				lastID = 0;
				var childOffset = $(this).offset();
				var parentOffset = $(this).parent().parent().offset();
				var childTop = childOffset.top - parentOffset.top;
				var clone = $(this).find("img").eq(0).clone();
				var top = childTop + 12 + "px";
				setTimeout(function() {
					$("#chatroom_profile p").addClass("animate");
					$("#chatroom_profile").addClass("animate");
				}, 100);
				setTimeout(function() {
					$("#chat-messages").addClass("animate");
					$(".cx, .cy").addClass("s1");
					setTimeout(function() {
						$(".cx, .cy").addClass("s2");
					}, 100);
					setTimeout(function() {
						$(".cx, .cy").addClass("s3");
					}, 200);
				}, 150);

				var CRname = $(this).find("p strong").html();
				var op_user_nick = $(this).find("p span").html();
				var crid = $(this).find("p i").html();
				var op_user_id = $(this).find("p b").html();

				$("#chatroom_profile p").html(CRname);
				$("#chatroom_profile span").html(op_user_nick);
				$("#chatroom_profile i").html(crid);
				$("#chatroom_profile b").html(op_user_id);

				$("#chatroomslist").fadeOut();
				$("#chatview").fadeIn();
				$("#close")
					.unbind("click")
					.click(function() {
						$("#chat-messages").removeClass("animate");
						$(".cx, .cy").removeClass("s1 s2 s3");
						setTimeout(function() {
							$("#chatview").fadeOut();
							$("#chatroomslist").fadeIn();
						}, 50);
					});
			} else {

				var childOffset = $(this).offset();
				var parentOffset = $(this).parent().parent().offset();
				var childTop = childOffset.top - parentOffset.top;
				var clone = $(this).find("img").eq(0).clone();
				var top = childTop + 12 + "px";

				$(clone).css({
					top: top
				}).addClass("floatingImg").appendTo("#chatbox");

				setTimeout(function() {
					$("#profile p").addClass("animate");
					$("#profile").addClass("animate");
				}, 100);
				setTimeout(function() {
					$(".cx, .cy").addClass("s1");
					setTimeout(function() {
						$(".cx, .cy").addClass("s2");
					}, 100);
					setTimeout(function() {
						$(".cx, .cy").addClass("s3");
					}, 200);
				}, 150);

				$(".floatingImg").animate({
					width: "68px",
					height: "68px",
					left: "115px",
					top: "40px",
				},
					200
				);

				var CRname = $(this).find("p strong").html();
				var op_user_nick = $(this).find("p span").html();
				var crid = $(this).find("p i").html();
				var op_user_id = $(this).find("p b").html();

				$("#profile p").html(op_user_nick);
				$("#profile span").html(op_user_id);

				$("#chatroom_profile p").html(CRname);
				$("#chatroom_profile span").html(op_user_nick);
				$("#chatroom_profile i").html(crid);
				$("#chatroom_profile b").html(op_user_id);

				var status = $(this).find("div");

				setTimeout(function() {
					if (status.hasClass("alreadyfriend")) {
						$("#deleteFriend").addClass("show");
					}
					else {
						if (status.hasClass("friendrequested")){
							$("#acceptFriendrequest").addClass("show");
							$("#deleteFriendrequest").addClass("show");
						}else if (status.hasClass("friendrequest")){
							$("#deleteChatroom").addClass("show");
						}else
							$("#createFriendrequest").addClass("show");
					}
					$("#deleteChatroom").addClass("show");
				}, 100);

				$("#chatroomslist").fadeOut();
				$("#profileview").fadeIn();

				$("#profile_close")
					.unbind("click")
					.click(function() {
						$("#profile, #profile p").removeClass("animate");
						$("#deleteFriend").removeClass("show");
						$("#deleteFriendrequest").removeClass("show");
						$("#acceptFriendrequest").removeClass("show");
						$("#createFriendrequest").removeClass("show");
						$("#deleteChatroom").removeClass("show");
						$(".cx, .cy").removeClass("s1 s2 s3");
						$(".floatingImg").animate({
							width: "40px",
							top: top,
							left: "12px"
						},
							200,
							function() {
								$(".floatingImg").remove();
							}
						);

						setTimeout(function() {
							$("#profileview").fadeOut();
							$("#chatroomslist").fadeIn();
						}, 50);
					});
			}
		});
	});


	$(".friend").each(function() {
		$(this).click(function() {

			var childOffset = $(this).offset();
			var parentOffset = $(this).parent().parent().offset();
			var childTop = childOffset.top - parentOffset.top;
			var clone = $(this).find("img").eq(0).clone();
			var top = childTop + 12 + "px";

			$(clone).css({
				top: top
			}).addClass("floatingImg").appendTo("#chatbox");

			setTimeout(function() {
				$("#profile p").addClass("animate");
				$("#profile").addClass("animate");
			}, 100);
			setTimeout(function() {
				$(".cx, .cy").addClass("s1");
				setTimeout(function() {
					$(".cx, .cy").addClass("s2");
				}, 100);
				setTimeout(function() {
					$(".cx, .cy").addClass("s3");
				}, 200);
			}, 150);

			$(".floatingImg").animate({
				width: "68px",
				height: "68px",
				left: "115px",
				top: "40px",
			},
				200
			);

			var name = $(this).find("p strong").html();
			var id = $(this).find("p span").html();
			$("#profile p").html(name);
			$("#profile span").html(id);

			var status = $(this).find("div");

			setTimeout(function() {
				if (status.hasClass("haschatroom"))
					$("#deleteChatroom").addClass("show");
				else
					$("#createChatroom").addClass("show");
				$("#deleteFriend").addClass("show");
			}, 100);

			$("#friendslist").fadeOut();
			$("#profileview").fadeIn();

			$("#profile_close")
				.unbind("click")
				.click(function() {
					$("#profile, #profile p").removeClass("animate");
					$(".cx, .cy").removeClass("s1 s2 s3");
					$("#deleteChatroom").removeClass("show");
					$("#createChatroom").removeClass("show");
					$("#deleteFriend").removeClass("show");

					$(".floatingImg").animate({
						width: "40px",
						top: top,
						left: "12px"
					},
						200,
						function() {
							$(".floatingImg").remove();
						}
					);

					setTimeout(function() {
						$("#profileview").fadeOut();
						$("#friendslist").fadeIn();
					}, 50);
				});
		});
	});
});
