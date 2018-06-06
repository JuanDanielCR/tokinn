$( document ).ready(function() {
    $("#customerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});
    
    function ajaxPost(){
    	var formData = {
    		numeroTarjeta : $("#numeroTarjeta").val(),
    		cvv :  $("#cvv").val()
    	}

    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : window.location + "api/login",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result.status == "Done"){
					$("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + 
												"Post Successfully! <br>" +
												"---> Customer's Info: FirstName = " + 
												result.data.firstname + " ,LastName = " + result.data.lastname + "</p>");
				}else{
					$("#postResultDiv").html("<strong>Error</strong>");
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    }
})