/**
 * 
 */ 

		
		var count2=0;
	
		$("#addOpt").click(function() {
			if(count2<10){
				var addOptss ='<div class="input-group col-xs-3"><input type="text" name="optName" placeholder="옵션이름"><span class="input-group-addon"><input type="text" name="optPrice" placeholder="금액(원)"><i class="glyphicon glyphicon-remove del"></i></span> </div>';
				$("#addOpts").append(addOptss);
				count2++;
			}else {
				alert("옵션 추가는 최대 10개까지 가능합니다.");
			}
		});
		
		$("#addOpts").on("click", ".del", function() {
			$(this).parent().parent().remove();
			//$(this).remove();
			count2--;
		});