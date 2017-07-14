/**
 * Created by faiter on 6/13/17.
 */
var options = [];

 $(document).ready(() => {

     var currentOptionID = 0;

     $("#addOptionButton").click(()=>{

         let name = $("#optionNameInput").val()

         console.log("Adding option "+name+" test2");

         $("#optionNameInput").val(""); // Empty input


         $("#optionList").append("<li id='item"+currentOptionID+"'>");
         $("#optionList").append(name);
         $("#optionList").append("<i id='deleteButton"+currentOptionID+"' class='waves-effect material-icons align-right'>delete</i>"); // delete icon
         $("#optionList").append("</li>");

         console.log( $("#item"+currentOptionID));

         $("#item"+currentOptionID).click(()=>{

             console.log("Hello!")

         })

         $("#deleteButton"+currentOptionID).click(()=>{

             const id = currentOptionID; // does not fucking work, fuck JS

             console.log(options)
             console.log(this.id)
             options[options.indexOf(name)] = null; // remove

             $("#item"+id).html("");
             console.log( $("#item"+id));

             console.log("delete")
             console.log(options)
         })

         options.push(name)
         currentOptionID += 1;
     });

     $("#makePollButton").click(()=>{

         console.log("Making poll!")

         var system = $('#systemForm input[name=votingSystems]:checked').val() // from SO :p

         console.log(options+" - "+system)

         $(location).attr('href', 'http://localhost:63342/Project1/Poll/web/html/pollPage.html?_ijt=jojb14agaeoosto0kblg8hkmun')

         renderList(options);

     });

 });