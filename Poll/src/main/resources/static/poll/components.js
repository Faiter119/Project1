/**
 * Created by faiter on 9/4/17.
 */

Vue.component('poll-navbar', {

    template: `<nav>
                    <div class="nav-wrapper">
                        <a v-on:click=goToIndex href="#" class="brand-logo"><!--<img src="logo.png">-->Logo</a>
                        <ul id="nav-mobile" class="right hide-on-med-and-down">
                            <li><a href="/CV_OlavH_2017_09_19.pdf">Resume</a></li>
                            <li><img id="githubImage" @click="goToGitHub" class="responsive-img" src="https://assets-cdn.github.com/images/modules/logos_page/GitHub-Logo.png"></li>
                        </ul>
                    </div>
                </nav>`,
    methods: {

        goToIndex: function () {

            window.location = "/";

        },
        goToGitHub: function () {

            window.location = "https://github.com/OlavH96";

        }



    }
})

console.log("Components activated")