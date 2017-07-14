var votingSystemRadioDiv = new Vue({
    el: '#votingSystemRadioDiv',
    data: {
        message: 'Voting System',
        systems: [
            { text: 'First Past The Post', type: "" },
            { text: 'Ranked Choice (Instant Runoff)' }
            ],
        selected: "First Past The Post",
    }
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

            console.log(optionListDiv.options)
            console.log(votingSystemRadioDiv.selected)

            //window.location = "/pollPage.html"


        }

    }
})