/*------------------------------------------------------------

 browserBlast v2.0.0
 Author: Mark Goodyear - http://www.markgoodyear.com
 Git: https://github.com/markgoodyear/browserblast
 Email: hello@markgoodyear.com
 Twitter: @markgdyr

 ------------------------------------------------------------*/

;var browserWarning = function (options) {

    "use strict";

    function getIEVersion() {
        var isSafari = navigator.vendor && navigator.vendor.indexOf('Apple') > -1 && navigator.userAgent && !navigator.userAgent.match('CriOS');
        var rv = -1;
        if (navigator.appName == 'Microsoft Internet Explorer')
        {
            var ua = navigator.userAgent;
            var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
            if (re.exec(ua) != null)
            rv = parseFloat( RegExp.$1 );

        }else if(isSafari) {
            var ua = 15;
            rv = ua;

        } else if (navigator.appName == 'Netscape') {
            var ua = navigator.userAgent;
            var re  = new RegExp("Trident/.*rv:([0-9]{1,}[\.0-9]{0,})");
            if (re.exec(ua) != null)
                rv = parseFloat( RegExp.$1 );

        }
        return rv;
    }

    var version = getIEVersion();

    function getWar(){
        swal({  title: "Close alert!",
                text: "Your window will close in 4 seconds.",
                timer: 4000,
                showCancelButton: true,
                showConfirmButton:false,
                confirmButtonText: "Yes",
                cancelButtonText: "Don't Close"

            },
            function(){
                window.open('', '_self', '');
                window.close();

            });
    }

    function getWarning(){
        swal({
                title: '<h3> Oops...</h3>',
                text: "Your browser is not supported. Please use Firefox or Chrome for better browsing experience.",
                html:true,
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false

            },

            function(){
                getWar()

            });
    }

    var version = getIEVersion();

    if ( version > 0 && version >= 11 || version === 15 )  {
        getWarning();
    }

};