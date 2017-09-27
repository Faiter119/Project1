var container = new Vue({
    el: '#container',
    data:{
        message: "Hello!",
        info: "Info goes here"
    },
    methods:{

        handleClientLoad: function () {
            gapi.load('client:auth2', container.initClient);
        },

        initClient: function() {

            gapi.client.init({
                apiKey: 'AIzaSyBl1J1dw-v6xdkCdwaPfTUtuEmZbthHvpg',
                //discoveryDocs: ["https://people.googleapis.com/$discovery/rest?version=v1"],
                clientId: '684640037444-po97f6t6p37cdrdg6td6gsf8cs3mm8us.apps.googleusercontent.com',
                scope: "profile"
            }).then(function () {
                // Listen for sign-in state changes.
                gapi.auth2.getAuthInstance().isSignedIn.listen(container.updateSigninStatus);

                // Handle the initial sign-in state.
                container.updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
            });
        },
        updateSigninStatus: function (isSignedIn) {
            if (isSignedIn) {
                container.makeApiCall();
            }
        },
        handleSignInClick: function (event) {
            gapi.auth2.getAuthInstance().signIn().then(function (user) {

                console.log(user)
                container.info = user
                container.message = "Log in successful";

            });
        },
        handleSignOutClick: function (event) {
            gapi.auth2.getAuthInstance().signOut();
            container.message = "Log out successful";

        },
        makeApiCall: function () {
            console.log("Pretending to make API call")
            /* gapi.client.people.people.get({
             'resourceName': 'people/me',
             'requestMask.includeField': 'person.names'
             }).then(function(response) {
             console.log('Hello, ' + response.result.names[0].givenName);
             }, function(reason) {
             console.log('Error: ' + reason.result.error.message);
             });*/
        }

    }
})

container.handleClientLoad()