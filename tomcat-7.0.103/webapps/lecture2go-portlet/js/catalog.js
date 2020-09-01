
$( function() {
	//turn off autocomplete
	$(document).on('focus', ':input', function() {
	    $(this).attr('autocomplete', 'off');
	});
	// only show the last terms
	var maxTerms = 4;
	$("ul.terms > li").slice(maxTerms).hide();
	// show the remaining terms
	$('#loadMoreTerms').click(function () {
	    $('ul.terms > li').show();
	    $('#loadMoreTerms').hide();
	});
	
	
	// add missing accessibility to the filter toggles (Liferay 6.2 - liferay ui tag does not natively support this)
	$(".toggler-header").attr("tabindex","0");
	$(".toggler-header").attr("role", "switch");
	$("#filter-institution").find(".toggler-header").attr("aria-label",Liferay.Language.get('show-filter') + " " + Liferay.Language.get('institution'));
	$("#filter-subinstitution").find(".toggler-header").attr("aria-label",+ Liferay.Language.get('show-filter') + " " + Liferay.Language.get('subinstitution'));
	$("#filter-term").find(".toggler-header").attr("aria-label",Liferay.Language.get('show-filter') + " " + Liferay.Language.get('term'));
	$("#filter-category").find(".toggler-header").attr("aria-label",Liferay.Language.get('show-filter') + " " + Liferay.Language.get('category'));

	// switch aria role attribute on manual toggle
	$(".toggler-header").on("click", function() {
		switchAriaForFilter($(this));
	});
	
	// set correct aria role attribute on page load
	$(".toggler-header").each(function() {
		setCorrectAriaForFilter($(this));
	});
	
	// toggles the panel if necessary
    toggleFilterPanel();
    
    // This is a workaround for binding the Javascript callbacks to the Videotiles. Usually the $(function() {...} should
    // execute when the dom is ready, but the liferay search container seems to build the html later then the onload event
    function start() {
		var tile = $("#_lgopenaccessvideos_WAR_lecture2goportlet_lectureseriesesSearchContainer div.videotile.wide");
		if (tile.size() > 0) {then()}
		else {
			setTimeout(start, 200);
		};
	}
	
	start();
	
	function then() {
		var tile = $("#_lgopenaccessvideos_WAR_lecture2goportlet_lectureseriesesSearchContainer div.videotile.wide");
		tile.on({
			click: function () {
					$button = $(this).find("button");
					$button.toggleClass("rotated");
					if ($button.hasClass("rotated")) {
						$button.attr("aria-label",Liferay.Language.get('close-video-list'));
						$button.attr("title",Liferay.Language.get('close-video-list'));
					} else {
						$button.attr("aria-label",Liferay.Language.get('open-video-list'));
						$button.attr("title",Liferay.Language.get('open-video-list'));
					}					
					
					$(this).siblings("div").find("ul").slideToggle();
			},
			mouseenter: function () {
				$(this).find("button").addClass("highlight");
			},
			mouseleave: function () {
				$(this).find("button").removeClass("highlight");
			}
		});
	
		// if a link on the tile is clicked, do not let the event bubble up to the tile itself
		tile.find("a").click(function(e) {
			e.stopPropagation();
		});
	}
});

function toggleFilterPanel(){
	
	MQS.add({
        ref: 'desktop',
        mediaQuery: '(min-width: 768px)', 
        action: () => {
        	$('.accordion-group').find('.toggler-content-collapsed').addClass('toggler-content-expanded').removeClass('toggler-content-collapsed');
		  	$('.accordion-group').find('.toggler-header-collapsed').addClass('toggler-header-expanded').removeClass('toggler-header-collapsed');
			$(".toggler-header").each(function() {
				setCorrectAriaForFilter($(this));
			});
        }
	 });
	
	MQS.add({
        ref: 'mobile',
        mediaQuery: '(max-width: 767px)', 
        action: () => {
        	$('.notFiltered').find('.toggler-content-expanded').addClass('toggler-content-collapsed').removeClass('toggler-content-expanded');
      		$('.notFiltered').find('.toggler-header-expanded').addClass('toggler-header-collapsed').removeClass('toggler-header-expanded');
      		$('.filtered').find('.toggler-header-collapsed').addClass('toggler-header-expanded').removeClass('toggler-header-collapsed');
  		    $('.filtered').find('.toggler-content-collapsed').addClass('toggler-content-expanded').removeClass('toggler-content-collapsed');
  			$(".toggler-header").each(function() {
  				setCorrectAriaForFilter($(this));
  			});
        }
	 });
}

function setCorrectAriaForFilter($element) {
	$togglerHeader = $(".toggler-header");
	if ($element.hasClass("toggler-header-collapsed")) {
		$element.attr("aria-checked",false);
	} else {
		$element.attr("aria-checked",true);
	}
}

function switchAriaForFilter($element) {
	$togglerHeader = $(".toggler-header");
	if ($element.attr("aria-checked") == "true") {
		$element.attr("aria-checked",false);
	} else {
		$element.attr("aria-checked",true);
	}

}
