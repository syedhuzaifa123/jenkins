function doLike(postId, userId) {

	console.log("Post ID: " + postId + ", User ID: " + userId);

	const d = {

		pId: postId,
		uId: userId,
		operation: "like"
	}

	$.ajax({
		url: "LikeServlet",
		method: "post",
		data: d,
		success: function(data, textStatus, jqXHR) {

			/*
			if (data.trim() === "true") {
				let counter = $("#likeCounter" + postId);
				let c = parseInt(counter.html());
				c++;
				counter.html(c);
				console.log("Data: " + data);
				console.log("Counter: " + c);
			}
			*/

			let counter = $("#likeCounter" + postId);
			let c = parseInt(counter.html());
			let icon = $("#likeBtnIcon" + postId);
			let likeColorClass = icon.data("like-color");


			if (data.trim() === "add") {

				c++;
				counter.html(" " + c);
				icon.addClass(likeColorClass);
				icon.removeClass("fa-thumbs-o-up").addClass("fa-thumbs-up");
			}

			else if (data.trim() === "delete") {
				c--;
				counter.html(" " + c);
				icon.removeClass(likeColorClass);
				icon.removeClass("fa-thumbs-up").addClass("fa-thumbs-o-up");
			}

		},

		error: function(jqXHR, textStatus, errorThrown) {
			console.error("AJAX Error:", errorThrown);
		}
	})

}



/*
$(document).ready(function() {

	alert("Document Loaded")

})
*/
