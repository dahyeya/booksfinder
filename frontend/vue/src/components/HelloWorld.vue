<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
    <div class="search-box">
      <div class="search-input">
        <input type="text" v-model="keyword" v-on:click.stop="showHistoryView" v-on:keyup.enter="search">
        <img src="/images/search-icon.png" v-on:click.stop="search">
        <img src="/images/prize-gold-icon.png" v-on:click.stop="showKeywordView">
      </div>
      <transition name="fade">
        <ul v-if="showHistory" class="search-history">
          <li v-for="(i, index) in 10" v-bind:key="i">
            <input type="radio" :id="i" name="box" :value="i" v-model="keyword" v-on:click="search">
            <label :for="i">
              <span>{{i}}</span>
              <span>{{i}}</span>
              <button>X</button>
            </label>
          </li>
        </ul>
      </transition>
      <transition name="fade">
        <ul v-if="showKeyword" class="search-history">
          <li v-for="(i, index) in 10" v-bind:key="i">
            <input type="radio" :id="i" name="box" :value="i" v-model="keyword" v-on:click="search">
            <label :for="i">
              <img v-if="(index==0)" src="/images/prize-gold-icon.png">
              <img v-if="(index==1)" src="/images/prize-silver-icon.png">
              <img v-if="(index==2)" src="/images/prize-bronze-icon.png">
              <span>{{i}}</span>
              <span>{{i}}</span>
            </label>
          </li>
        </ul>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      keyword : '',
      showKeyword : false,
      showHistory : false
    }
  },
  methods : {
    showHistoryView (e) {
      this.showHistory = !this.showHistory
      this.showKeyword = false;
    },
    search () {
      console.log(this.keyword);
    },
    showKeywordView(){
      this.showHistory = false;
      this.showKeyword = !this.showKeyword
    },
    closeView(){
      this.showHistory = false;
      this.showKeyword = false;
    }
  },
  created(){
    document.onclick = this.closeView;
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.search-box{
  width :300px;
}
.search-input{
  width:100%;
  position: relative;
  border : 1px solid #000000;

}
.search-input>input[type=text]{
  background: none;
  border: none;
  width: calc(100% - 60px);
  padding: 8px 44px 8px 16px;
}
.search-input>img{
  position: absolute;
  top: 0;
  right: 6px;
  width: 24px;
  height : 24px;
  outline: none;
  padding: 4px;
  cursor: pointer;
}
.search-history{
  width:100%;
}
.search-history{
  padding:0;
  margin:0;
  width:100%;
  border : 1px solid #000000;
  list-style: none;
  max-height: auto;
  overflow:hidden;
  -webkit-transition: max-height 0.8s;
	-moz-transition: max-height 0.8s;
	transition: max-height 0.8s;
}

.search-history>li{
  border-bottom: 1px solid #888888;
}
.search-history>li:last-of-type{
  border-bottom : none;
}
.search-history>li>label{
  padding:8px 16px;
  display: block;
  font-size: 13px;
  text-align: left;
  cursor: pointer;
}
.search-history>li>label>img{
  height: 16px;
  margin-right: 16px;
}
.search-history>li>input[type=radio]{
  display: none;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity .1s;
}
.fade-enter, .fade-leave-to{
  opacity: 0;
}
</style>
// timeConvertGap : function(start, end){
// 			let gap = toDate(end).getTime() - toDate(start).getTime();
			
// 			if(gap > 1000 * 60 * 60 * 24 * 365){
// 				return (Math.floor(gap / (1000 * 60 * 60 * 24 * 365))) +"년";
// 			}else if(gap > 1000 * 60 * 60 * 24 * 30){
// 				return (Math.floor(gap / (1000 * 60 * 60 * 24 * 30))) +"개월";
// 			}else if(gap > 1000 * 60 * 60 * 24){
// 				return (Math.floor(gap / (1000 * 60 * 60 * 24))) +"일";
// 			}else if(gap > 1000 * 60 * 60){
// 				return (Math.floor(gap / (1000 * 60 * 60))) +"시간";
// 			}else if(gap > 1000 * 60){
// 				return (Math.floor(gap / (1000 * 60))) +"분";
// 			}else if(gap > 1000){
// 				return (Math.floor(gap / 1000)) +"초";
// 			}else{
// 				return "계산 할 수 없음";
// 			}

			
			
// 			function toDate(value){
// 				var year;
// 				var month;
// 				var day;
// 				var hour;
// 				var min;
// 				var sec;
// 				if(typeof value =="string" && value.length==14){
// 					year = value.substring(0, 4);
// 					month = value.substring(4,6);
// 					day = value.substring(6,8);
// 					hour = value.substring(8,10);
// 					min = value.substring(10,12);
// 					sec = value.substring(12,14);

// 				}else if(typeof value =="string" && value.length==12){
// 					year = value.substring(0, 4);
// 					month = value.substring(4,6);
// 					day = value.substring(6,8);
// 					hour = value.substring(8,10);
// 					min = value.substring(10,12);
	
// 				}
// 				year = Number(year);
// 				month = Number(month);
// 				day = Number(day);
// 				hour = Number(hour);
// 				min = Number(min);
// 				sec = Number(sec);
				
// 				var date = new Date();
// 				date.setFullYear(year);
// 				date.setMonth(month - 1);
// 				date.setDate(day);
// 				date.setHours(hour);
// 				date.setMinutes(min);
// 				date.setSeconds(sec);
// 				date.setMilliseconds(0);

// 				return date;
// 			}