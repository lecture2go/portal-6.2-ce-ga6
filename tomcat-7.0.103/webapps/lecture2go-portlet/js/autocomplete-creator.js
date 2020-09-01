$( function() {
	if(!assignedCreators) assignedCreators="";
	showCreatorsList(assignedCreators);
});

function showCreatorsList(data) {
	$.template( "filesTemplate", $("#created") );
	$.tmpl( "filesTemplate", data ).appendTo( "#creators" );
}


function autocompleteCreator($creatorInputObject, validationFunction, newCreatorHandler) {
	// validationFunction is not used any more...
	avoidClosing = false;
	$creatorInputObject.autocomplete({
	    source: function(request, response) {
	        var results = $.ui.autocomplete.filter(allCreatorsInJQueryAutocompleteFormat, request.term);
	        response(results.slice(0, 10));
	    },
	    messages: {
	        noResults: '',
	        results: function() {}
	    },
		minLength: 1,
		open: function(event, ui) {
			$('.ui-autocomplete').append('<li id="addNewCreator" class="newcreator">' + Liferay.Language.get('add-new-creator') + ' <span class="icon-large icon-plus-sign"></span></li>');
			$( "#addNewCreator" ).on( "click", function() {
				/* empty the creator input field */
				$creatorInputObject.val('');
				c++;
				appendCreator(c, newCreatorHandler);
				$creatorInputObject.autocomplete('close');
			});
		},
        response: function(event, ui) {
            if (ui.content.length === 0) {
            	avoidClosing = true;
	        }
        },
        close: function(event, ui) {
        	if (avoidClosing) {
        		$("ul.ui-autocomplete li:not(:last)").remove();
            	$("ul.ui-autocomplete").show();
            	avoidClosing = false;
        	}
        },
		select: function (event, ui) {
			hasFormChanged = true;
			if(ui.item.id>0){
  		        var vars = getJSONCreator(ui.item.id);
  		        $.template( "filesTemplate", $("#created") );
  		        $.tmpl( "filesTemplate", vars ).appendTo( "#creators" );
  		        if( typeof newCreatorHandler == "function" ) {
  		        	newCreatorHandler();
  		        }
  				/* empty the creator input field */
  		     	ui.item.value="";
  			}
		}
	});
}

function appendCreator(c, newCreatorHandler){
	$(function () {
    	var vars = {'counter':c};
    	$.template( "filesTemplate", $("#newCreator") );
    	$.tmpl( "filesTemplate", vars ).appendTo( "#creators" );
    	// set a event handler for the newly created creator input fields
    	$('#nc'+c).keyup(function() {
    		if( typeof newCreatorHandler == "function" ) {
				newCreatorHandler();
			}
    	});
    	// the user can save the new creator(s) with an enter key press
    	$('#nc'+c).on('keypress', function(event) {
    		if(event.which == 13) {
    			updateCreators();
    		}
    	});
	});
};

function getJSONCreator (data){
	var ret;
	/* we can not access the portlet namespace from the external js file directly so we need to build the variable property name this way: */
	var creatorId = namespace + "creatorId";
	var dataJSON = {};
	dataJSON[creatorId] = data;
	
	$.ajax({
		  type: "POST",
		  url: getJSONCreatorURL,
		  dataType: 'json',
		  data: dataJSON,
		  global: false,
		  async:false,
		  success: function(data) {
		    ret = data;
		  }
	})
	return ret;
}

function getJSONAllCreators (videoId){
	var ret;	
	var videoIdKey = namespace + "videoId";
	var dataJSON = {};
	dataJSON[videoIdKey] = videoId;
	console.log(dataJSON);
	$.ajax({
		  type: "POST",
		  url: getJSONAllCreatorsURL,
		  dataType: 'json',
		  data: dataJSON,
		  global: false,
		  async:false,
		  success: function(data) {
		    ret = data;
		  }
	})
	console.log(ret);
	return ret;
}

var creatorsArray = [];

function getJsonCreatorsArray(){
	loadCreators();
	return creatorsArray;
}

function updateCreators(){
	loadCreators();
	updateCreatorOnServer(creatorsArray);
}

var creatorsArray = [];
function loadCreators(){
	creatorsArray = [];
	$('#creators').children().each(function(n){
		var parameters = {};
		var $div = $(this);
		var id = $div.attr('id');
		if(id.indexOf("nc")==-1){
			parameters['creatorId'] = $div.find('input[name = '+namespace+'creatorId]').val();
			parameters['firstName'] = $div.find('input[name = '+namespace+'firstName]').val();
			parameters['lastName'] = $div.find('input[name = '+namespace+'lastName]').val();
			parameters['middleName'] = $div.find('input[name = '+namespace+'middleName]').val();
			parameters['jobTitle'] = $div.find('input[name = '+namespace+'jobTitle]').val();
			parameters['gender'] = "";
			parameters['fullName'] = $div.find('input[name = '+namespace+'fullName]').val();
			parameters['affiliation'] = $div.find('input[name = '+namespace+'affiliation]').val();
			parameters['orcidId'] = $div.find('input[name = '+namespace+'orcidId]').val();
		}else{
			parameters['creatorId'] = "0";
			parameters['firstName'] = $div.find('input[name = '+namespace+'firstName]').val().trim();
			parameters['lastName'] = $div.find('input[name = '+namespace+'lastName]').val().trim();
			parameters['middleName'] = $div.find('input[name = '+namespace+'middleName]').val().trim();
			parameters['jobTitle'] = $div.find('input[name = '+namespace+'jobTitle]').val().trim();
			parameters['gender'] = "";
			parameters['fullName'] = (parameters['jobTitle'].trim()+" "+(parameters['firstName'].trim()+" "+parameters['middleName'].trim()).trim()+" "+parameters['lastName'].trim()).trim();		
			parameters['affiliation'] = $div.find('input[name = '+namespace+'affiliation]').val();
			parameters['orcidId'] = $div.find('input[name = '+namespace+'orcidId]').val();
		}
		if(parameters['firstName'].length>0 && parameters['lastName'].length>0){
			creatorsArray[n]=parameters;
		}
	});	
}
