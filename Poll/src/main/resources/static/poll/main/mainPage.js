var votingSystemRadioDiv = new Vue({
    el: '#votingSystemRadioDiv',
    data: {
        title: "",
        systems: [
            { text: 'First Past The Post', value: "FIRST_PAST_THE_POST"},
            { text: 'Ranked Choice (Instant Runoff)', value: "RANKED_CHOICE" }
            ],
        selected: "FIRST_PAST_THE_POST",

    },
})

var optionListDiv = new Vue({
    el: '#optionListDiv',
    data: {
        message: "Options",
        optionName: null,
        options: []
    },
    methods: {
        addNewOption: function () {

            if(this.optionName != null && this.optionName != ""){
                this.options.push(this.optionName)
                this.optionName = null // amazing
            }
            else { // empty field
                makePollDiv.makePoll()
            }

        },
        deleteButtonPressed:function (option) {

            console.log(option)
            this.options.splice(this.options.indexOf(option), 1)

        },
        popLast: function () {
            if(this.optionName == null || this.optionName == ""){
                this.options.pop()
            }
        }
    }
});

var makePollDiv = new Vue({
    el: '#makePollDiv',
    methods: {
        makePoll: function () {

            var data = JSON.stringify({
                "title":votingSystemRadioDiv.title,
                "pollType":votingSystemRadioDiv.selected,
                "options":optionListDiv.options
            })

            console.log(data)

            /*http://olavhus.tihlde.org:8081*/

            $.ajax({
                url: "/polls",
                type: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: data,
                //dataType:'Application/JSON', // Causes error to fire on status 200
                success: function (response) {
                    console.log("Success");
                    console.log(response); // the number of the poll you just created
                    window.location = "../poll/pollPage.html?pollId="+response

                },
                error: function (error) {

                    console.log(error)

                }
            });

        }
    }
})
