(function AnimationFacade(){
    window.animationFacade = new function(){
        scriptLoader.loadScript("/js/common/animation/roll.js");
        
        this.rollInHorizontal = function(element, parent, time){return roll.rollInHorizontal(element, parent, time)};
        this.rollOutHorizontal = function(element, parent, time){return roll.rollOutHorizontal(element, parent, time)};
    }
})();