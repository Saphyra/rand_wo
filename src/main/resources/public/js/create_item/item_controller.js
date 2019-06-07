(function ItemController(){
    events.ADD_COLUMN = "add_column";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_COLUMN},
        function(event){
            const columnData = event.getPayload();
            $("#no-columns").hide();

            const container = document.getElementById("columns");
                const column = document.createElement("DIV");
                    column.classList.add("column");

                    const columnHead = document.createElement("DIV");
                        columnHead.classList.add("column-head");
                        columnHead.innerHTML = columnData.value;
                column.appendChild(columnHead);

                    const columnBody = document.createElement("DIV");
                        const textarea = document.createElement("TEXTAREA");
                            textarea.id = columnData.id;
                            //TODO localized placeholder
                    columnBody.appendChild(textarea);
                column.appendChild(columnBody);

                    const removeButton = document.createElement("BUTTON");
                        removeButton.innerHTML = "X";

                        removeButton.onclick = function(){
                            container.removeChild(column);
                            eventProcessor.processEvent(new Event(events.REMOVE_COLUMN, {keyId: columnData.id, type: columnData.type}));
                        }
                column.appendChild(removeButton);

            container.appendChild(column);
        }
    ));
})();