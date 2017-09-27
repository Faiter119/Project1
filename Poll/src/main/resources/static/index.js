/**
 * Created by faiter on 6/29/17.
 */

var navContainer = new Vue({

    el: "#navContainer"

})

var container = new Vue({
    el: '#container',
    methods: {

        vueLogoClicked: function () {

            window.location = "https://vuejs.org/"
        },
        materializeLogoClicked: function () {
            window.location = "http://materializecss.com/"

        },
        tihldeLogoClicked:function () {
            window.location = "https://tihlde.org/"

        }

    }
})