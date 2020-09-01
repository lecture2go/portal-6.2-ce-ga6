<script type="text/javascript">jwplayer.key="your-licence-here";</script>

<style>
<!--
.jw-reset.jw-settings-content-item {
    line-height: 13px;
}

.jw-breakpoint-4 .jw-settings-menu, .jw-breakpoint-3 .jw-settings-menu {
    width: 220px;
}

.jwplayer .jw-rightclick.jw-open {
    display: none;
}
/*
.jw-icon.jw-icon-inline.jw-button-color.jw-reset.jw-icon-cc.jw-settings-submenu-button {
	display: none !important;
}

.jw-icon.jw-icon-inline.jw-button-color.jw-reset.jw-settings-captions.jw-submenu-captions {
	display: none !important;
}
*/
-->
</style>

<script>
    $(function() {
        // herausfinden ob es ein tablet/smartphone ist
        var isTouchDevice = 'ontouchstart' in document.documentElement;

        // Die Adresse des Web- und Videoservers ermitteln
        var server = "#";

        // Diese Funktion wird genutzt um die Url Parameter auszulesen
        var getUrlParameter = function(sParam){
            var sPageURL = window.location.search.substring(1);
            var sURLVariables = sPageURL.split('&');
            for (var i = 0; i < sURLVariables.length; i++){
                var sParameterName = sURLVariables[i].split('=');
                if (sParameterName[0] == sParam){
                    return sParameterName[1];
                }
            }
        };
        
        // Start- und Endzeit der Zitatfunktion ermitteln (Durch die URL Parameter)
        var frameStart = getUrlParameter('start');
        var frameEnd = getUrlParameter('end');
        var fs = <%=timeStart%>;
        var fe = <%=timeEnd%>;
        var citationAllowed = <%=video.getCitation2go()%>;
        
		if(citationAllowed==1 && fs>0 && fe>fs){
	        frameStart = <%=timeStart%>;
	        frameEnd = <%=timeEnd%>;		
		}
		
		
        // Hier wird der JW-Player initialisiert
        // Interessant ist hierbei, dass es mehrere Quellen geben kann
        var pla = jwplayer('player1').setup({
            width: "100%",
            aspectratio: "16:9",
            playbackRateControls: [0.75, 1, 1.25, 1.5],
            cast: {},
            playlist: [{
                <c:if test="${video.is360()}">
                        stereomode: 'monoscopic',
                </c:if>
                image: "${video.image}",
                sources: ${video.jsonPlayerUris},
                <c:if test="${video.hasCaption || video.hasChapters}">
                        tracks: ${video.jsonPlayerTracks},
                </c:if>
            }],

            hlshtml: true,
            androidhls: true
        });
        
        pla.on('ready', function(){

         	// Inputfelder für Start und Ende der Zitate / Kapitel speichern 
            var $inputTimeStart = $("#<portlet:namespace></portlet:namespace>timeStart").val("");
            var $inputTimeEnd = $("#<portlet:namespace></portlet:namespace>timeEnd").val("");
            var $citation = $("#<portlet:namespace></portlet:namespace>citation").val("");
            var $citationiframe = $("#<portlet:namespace></portlet:namespace>citationiframe").val("");

            var $chapters = $('#chapters');
            var $chapterDivs = $chapters.find("li.chaptertile");
            
            // Chapter ids und Zeiten in Object für spätere Abfragen speichern
            var chapters = [];
            for (var i = 0; i < $chapterDivs.length; i++) {
            	// Array chapters enthält Triple aus id, Anfangs- und Endzeit der Kapitel
            	var chapter = {
            			id : $chapterDivs.eq(i).attr("id"),
            			begin : timeToSeconds($chapterDivs.eq(i).attr("begin")),
                    	end : timeToSeconds($chapterDivs.eq(i).attr("end"))
            	};
            	chapters[i] = chapter;
            }

            if (frameStart && frameEnd) {
                // Sollten sich die Start- und Endzeit in den URL Parametern befinden
                // wird in diesen Abschnitt dafür gesorgt das man auch nur das Entsprechende
                // Videomaterial zu sehen bekommt


                // iOS und Android unterstützen seek nur wenn der Nutzer
                // selbst manuell das vide gestartet hat. Wir werden den start des Zitates
                // später anders lösen
                if (!isTouchDevice) {
                	jwplayer().on('firstFrame', function() { 
                		jwplayer().play();
                		jwplayer().seek(frameStart);
                	});
                }
            }

                
            // Event listener alle 100 ms während playback
            pla.on('time', function(event) {
                // Sicher stellen, dass der gewählte Zeitraum eingehalten wird

                var pos =  Math.floor(event.position);

                if (pos < frameStart && isTouchDevice) {
                    // Nur unter iOS und Android nötig,
                    jwplayer().seek(frameStart);
                } else if (pos > frameEnd) {
                    jwplayer().seek(frameStart);
                    jwplayer().pause();
                }
            
                // Falls neues Kapitel beginnt, zu Kapitel scrollen und hervorheben
                for (var i = 0; i < chapters.length; i++) {
                    // Falls Player in ein Kapitel eintritt, Kapitel Klasse 'active' setzen
                    var $cur = $("#" + chapters[i].id);
                	if ( pos == chapters[i].begin && !$cur.hasClass("active")) {
                		$cur.addClass("active").siblings().removeClass("active");
                		
                		var off1 = Math.floor( $cur.offset().top );
                		var off2 = Math.floor( $chapters.offset().top );
                		var diff = off1 - off2;
                		var target = $chapters.scrollTop() + diff;
                		
                		$chapters.animate({ scrollTop: target}, 600);
                		
                	} else if ( pos == chapters[i].end + 1 ){
                		$cur.removeClass("active");
                	}
                }
            });

            // Diese Stelle ist wiederum nur auf PC nötig.
            // Hiermit wird verhindert, dass der Nutzer per Tastatur
            // aus den Zitatsbereich herausspult
            if (!isTouchDevice) {
            	pla.on('seek', function(event) {
                    var pos =  event.position;

                    if (Math.ceil(pos) < Math.ceil(frameStart) || Math.ceil(pos) > Math.ceil(frameEnd)) {
                        jwplayer().seek(frameStart);
                        jwplayer().pause();
                    }
                });
            }

            $('#fullVideo').show();
        
        	// Sollte der Nutzer auf die Kapitel klicken,
            // wird zur entsprechenden Stelle gesprungen
            $chapters.find('li.chaptertile').on('click', function(event) {
                
            	$(this).addClass("active").siblings().removeClass("active");
            	var sec = $(this).attr("begin");
            	sec = timeToSeconds(sec);
                jwplayer().seek(sec);
                $("html, body").animate({ scrollTop: 0 }, "slow");
            });
            
            $chapters.find("input[alt='delete']").on("click", function(event) {
            	event.stopPropagation();
            });

            // Im nachfolgenden Abschnitt wird den Nutzer ermöglicht
            // eigene Zitate zu erstellen und zu teilen

            var startFrameTime = undefined;
            var endFrameTime = undefined;
            var startTimeStr = undefined;
            var endTimeStr = undefined;


            // Benutzer setzt Start des Clips
            $inputTimeStart.click(setStartTime);
            
            $inputTimeStart.keypress(function() {
                if (event.which == 13) setStartTime();
            });
            
           	function setStartTime() {
           		startFrameTime = Math.round(jwplayer().getPosition());
                startTimeStr = secondsToTime(Math.floor(startFrameTime));

                $inputTimeStart.val(startTimeStr);
                
                if (startFrameTime && endFrameTime) {
                	// Falls Startzeit nach Endzeit liegt, Zeiten angleichen
                	if (startFrameTime > endFrameTime) {
                		endFrameTime = startFrameTime;
                		endTimeStr = secondsToTime(Math.floor(endFrameTime));
                		$inputTimeEnd.val(endTimeStr);
                		
                	}
                	generateClipLink (Math.round(startFrameTime), Math.round(endFrameTime));
                	console.log("start: " + startFrameTime + ", end: " + endFrameTime);
                }
           	}

            // Benutzer setzt Ende des Clips
            $inputTimeEnd.click(setEndTime);
            
            $inputTimeEnd.keypress(function() {
                if (event.which == 13) setEndTime();
            });
            
            function setEndTime() {
            	endFrameTime = jwplayer().getPosition();
                EndTimeStr = secondsToTime(Math.floor(endFrameTime));

                $inputTimeEnd.val(EndTimeStr);

                if (startFrameTime && endFrameTime && startFrameTime<endFrameTime) {
                	// Falls Startzeit nach Endzeit liegt, Zeiten angleichen
                	if (startFrameTime > endFrameTime) {
                		startFrameTime = endFrameTime;
                		startTimeStr = secondsToTime(Math.floor(startFrameTime));
                		$inputTimeStart.val(startTimeStr);
                		
                	}
                	generateClipLink (Math.round(startFrameTime), Math.round(endFrameTime));
                	console.log("start: " + startFrameTime + ", end: " + endFrameTime);
                }
                validateClipTime();
           	}
            
            function generateClipLink (firstFrame, lastFrame) {
            	$citation.val("${video.url}"+"/"+firstFrame+"/"+lastFrame);
            	var vid = <%=video.getVideoId()%>;
            	var host ='<%=PropsUtil.get("lecture2go.web.root")%>';
            	
            	var iframe="<iframe src='"+host+"/lecture2go-portlet/player/iframe/?v="+vid+"/"+firstFrame+"/"+lastFrame+"' frameborder='0' width='647' height='373' allowfullscreen></iframe>";
            	$citationiframe.val(iframe);
            }
        });
        
    	/* this is a workaround for our jwplayer version which renders captions wrong when video is fullscreen on the iphone */
		var isIphone = jwplayer().getEnvironment().OS.iPhone;
    	// full screen event is triggered when entering or exiting fullscreen
    	jwplayer().on('fullscreen', function(){
    		if (isIphone) {
    			if (jwplayer().getFullscreen()) {
    			    var screenHeight = window.screen.availHeight;
    			    // set the font size in relation to the screenHeight
    			    var fontSize = Math.round(screenHeight * 0.07);
    		        jwplayer().setCaptions({"fontSize": fontSize});
    			} else {
    				// not fullscreen, reset the font size
    				jwplayer().setCaptions({"fontSize": "initial"});
    			}
    		}
    	});
    	/* end workaround */
    	
    	// overwrite the default jwplayer error message for a custom message
    	jwplayer().on('error', function(e){
   			$(".jw-error-text").text('<liferay-ui:message key="player-error"/>');
    	});
    });
    


</script>

<div id="player1"></div>
