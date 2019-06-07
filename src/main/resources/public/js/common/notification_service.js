(function NotificationService(){
    window.notificationService = new function(){
        scriptLoader.loadScript("/js/common/animation/animationfacade.js");
        
        this.showSuccess = showSuccess;
        this.showError = showError;
        this.showMessage = showMessage;
        
        $(document).ready(function(){
            printStoredMessages();
        });
    }
    
    /*
    Shows a success message.
    Arguments:
        - message: the text to show.
    */
    function showSuccess(message){
        try{
            showMessage(message, "green");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Shows an error message.
    Arguments:
        - message: the text to show.
    */
    function showError(message){
        try{
            showMessage(message, "red");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Shows a notification message with the given color.
    Arguments:
        - message: the text to show.
        - bgColor: the background color of the notification.
    */
    function showMessage(message, bgColor){
        try{
            const container = document.getElementById("notificationcontainer") || createContainer();
            const messageElement = createMessageElement(message, bgColor);
            messageElement.onclick = function(){
                container.removeChild(messageElement);
            }
            
            animationFacade.rollInHorizontal(messageElement, container, 300)
            .then(function(){
                return new Promise(function(resolve, rejejct){
                    setTimeout(function(){resolve();}, 10000)
                });
            })
            .then(function(){
                return animationFacade.rollOutHorizontal(messageElement, container, 300);
            })
            .then(function(){
                container.removeChild(messageElement);
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }

        function createContainer(){
            try{
                const container = document.createElement("DIV");
                    container.id = "notificationcontainer";
                    container.style.position = "fixed";
                    container.style.left = "5px";
                    container.style.top = "5px";
                    container.style.maxWidth = "calc(100vw - 10px)";
                document.body.appendChild(container);
                return container;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }

        function createMessageElement(message, bgColor){
            try{
                const wrapper = document.createElement("DIV");
                    wrapper.style.display = "inline-block";
                    
                    const element = document.createElement("DIV");
                        element.innerHTML = message;
                        element.style.backgroundColor = bgColor;
                        element.classList.add("button");
                        element.style.fontSize = "1.5rem";
                        element.style.margin = "0.25rem";
                        element.style.padding = "0.25rem";
                        element.style.whiteSpace = "nowrap";
                wrapper.appendChild(element);
                return wrapper;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }

    /*
    Shows the messages stored in sessionStorage.
    */
    function printStoredMessages(){
        try{
            if(sessionStorage.errorMessage != undefined && sessionStorage.errorMessage != null){
                showError(sessionStorage.errorMessage);
                delete sessionStorage.errorMessage;
            }
            
            if(sessionStorage.successMessage != undefined && sessionStorage.successMessage != null){
                showSuccess(sessionStorage.successMessage);
                delete sessionStorage.successMessage;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();