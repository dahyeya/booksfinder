<template>
  <div class="books-index">
    <header class="books-index-header">
        <h4>BOOK'S FINDER</h4>
        <div class="search-box">
          <div class="search-input" v-on:click.stop="showHistoryView" v-on:keyup.enter="search">
            <input type="text" v-model="keyword">
            <img src="../assets/search-icon.png" v-on:click.stop="search">
            <img src="../assets/prize-gold-icon.png" v-on:click.stop="showKeywordView">
          </div>
          <transition name="fade">
            <ul v-if="showHistory" class="search-history noimg">
              <li v-for="(item, index) in history" v-bind:key="item">
                <input type="radio" :id="item.keyword" name="box" :value="item.keyword" v-model="keyword" @click="waitSearch">
                <label :for="item.keyword">
                  <span>{{item.keyword}}</span>
                  <span>{{toTimestampString(item.issue)}}</span>
                </label>
              </li>
            </ul>
          </transition>
          <transition name="fade">
            <ul v-if="showKeyword" class="search-history">
              <li v-for="(item, index) in top10" v-bind:key="item">
                <input type="radio" :id="item.keyword" name="box" :value="item.keyword" v-model="keyword" @click="waitSearch">
                <label :for="item.keyword">
                  <img v-if="(index==0)" src="../assets/prize-gold-icon.png">
                  <img v-if="(index==1)" src="../assets/prize-silver-icon.png">
                  <img v-if="(index==2)" src="../assets/prize-bronze-icon.png">
                  <span>{{item.keyword}}</span>
                  <span>{{item.search}}</span>
                </label>
              </li>
            </ul>
          </transition>
        </div>
        <button v-on:click="logout" class="close-button"></button>
    </header>
    <section class="books-index-section">
        <ul v-if="books.length!=0" class="books-result">
          <li v-for="book in books" v-bind:key="book" v-on:click="detail(book)">
            <div>
              <img :src="book.thumbnail">
            </div>
            <div>
              <span>{{book.title}}</span>
              <!-- <span>{{book.authors}}</span> -->
              <span>{{book.publisher}}</span>
            </div>
            <div>
              <span>{{(book.sale_price>0 && book.price>0) ? toPrice(book.sale_price) : toPrice(book.price)}}</span>
              <span v-if="(book.sale_price>0 && book.price>0)" class="sale">{{(100 - Math.round((book.sale_price/book.price)*100))+"%"}}</span>
            </div>
          </li>
        </ul>
        <div v-else class="result-empty">
          <img src="../assets/book-icon.png">
          <div>
            {{resultMessage}}
          </div>
        </div>
    </section>
    <footer class="books-index-footer">
      <vue-ads-pagination
          :total-items="total"
          :max-visible-pages="5"
          :page="page"
          :loading="loading"
          @page-change="pageChange"
          @range-change="rangeChange"
      >
          <template slot-scope="props">
              <div class="vue-ads-pr-2 vue-ads-leading-loose" style="display:none">
                  Items {{ props.start }} tot {{ props.end }} van de {{ props.total }}
              </div>
          </template>
          <template
              slot="buttons"
              slot-scope="props"
          >
              <vue-ads-page-button
                  v-for="(button, key) in props.buttons"
                  :key="key"
                  v-bind="button"
                  @page-change="pageChange(page = button.page)"
              />
          </template>
        </vue-ads-pagination>
    </footer>
    <template>
      <div v-if="showing" class="books-detail">
        <section class="books-detail-section">
            <div>
              <img :src="book.thumbnail">
              <div>
                <div>{{book.title}}</div>
                <div>
                  <span>출판</span>
                  <span>{{book.publisher}} | {{book.datetime.substring(0, book.datetime.indexOf("T"))}}</span>
                </div>
                <div>
                  <span>저자</span>
                  <span>{{book.authors.join(", ")}}</span>
                </div>
                <div>
                  <span>번역</span>
                  <span>{{book.translators.join(", ")}}</span>
                </div>
                <div>
                  <span>ISBN</span>
                  <span>{{book.isbn}}</span>
                </div>
                <div>
                  <span v-if="book.price>0">정가</span>
                  <span v-if="book.price>0">{{toPrice(book.price)}}</span>
                  <span v-if="book.sale_price>0">할인가</span>
                  <span v-if="book.sale_price>0">{{toPrice(book.sale_price)}}</span>
                </div>
              </div>
            </div>
            <div>
              <h4>소개</h4>
              <div>
                {{book.contents}}
              </div>
            </div>
            <button v-on:click="cloase" v-on:keyup.delete="cloase" class="close-button"></button>
        </section>
      </div>
    </template>
  </div>
</template>
<script>
import '../../node_modules/@fortawesome/fontawesome-free/css/all.css'
import '../../node_modules/vue-ads-pagination/dist/vue-ads-pagination.css'

import VueAdsPagination, { VueAdsPageButton } from 'vue-ads-pagination';

export default {
  name: 'Login',
  components: {
      VueAdsPagination,
      VueAdsPageButton,
  },

  data () {
    return {
      total : 0,
      books : [],
      showing : false,
      resultMessage : "검색을 시작해주세요",
      icon : {
        search : "../assets/search-icon.svg"
      },
      loading: false,
      page: 0,
      keyword : '',
      showKeyword : false,
      showHistory : false,
      top10 : [],
      history : []
    }
  },
  methods: {
    logout(){
      this.$Axios.delete("/api/session")
        .then(res => {
          this.$router.replace("/")
        }, res => {
          console.log("logout failed")
        })
    },
    search (page) {
        this.closeView();
        if(!page){
          this.page = 0;
        }
        if(this.keyword==""){
          alert("빈 값으로 검색을 할 수 없습니다.");
          return;
        }
        let url = '/api/books?query='+this.keyword+'&page='+(this.page+1);
        this.$Axios.get(url)
        .then(res => {
            this.books = res.data.documents;
            this.total = res.data.meta.pageable_count;

            if(this.books.length==0){
              this.resultMessage = "도서를 찾을 수 없습니다.";
            }
        }, res => {
          this.resultMessage = "일시적인 지연이 발생하였습니다.";
        });
    },
    waitSearch(){
      setTimeout(this.search, 100);
    },
    detail (book) {
      this.book = book;
      this.showing = true;
    },
    toPrice(value){
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"원";
    },
    toTimestampString(value){
			let gap = new Date().getTime() - Number(value);
      let date = new Date();
      date.setTime(Number(value));

      let dateFormatString = date.getFullYear()+"."+(date.getMonth()+1)+"."+date.getDate();

      if(gap > 1000 * 60 * 60 * 24 * 365){         
        return dateFormatString;
			}else if(gap > 1000 * 60 * 60 * 24 * 30){
        return dateFormatString;
			}else if(gap > 1000 * 60 * 60 * 24){
        return dateFormatString;
			}else if(gap > 1000 * 60 * 60){
				return (Math.floor(gap / (1000 * 60 * 60))) +"시간전";
			}else if(gap > 1000 * 60){
				return (Math.floor(gap / (1000 * 60))) +"분전";
			}else if(gap > 1000){
        return (Math.floor(gap / 1000)) +"초전";
        //return dateFormatString;
			}else{
				return "방금전";
			}
    },
    cloase(){
      this.showing = false;
    },
    pageChange (page) {
        this.page = page;
        this.search(page);
    },  
    rangeChange (start, end) {
        // console.log(start, end);
    },
    showHistoryView (e) {
      this.showHistory = !this.showHistory
      this.showKeyword = false;

      let url = '/api/books/history';
      this.$Axios.get(url)
      .then(res => {
          this.history = res.data;
      });
    },
    // search () {
    //   console.log(this.keyword);
    // },
    showKeywordView(){
      this.showHistory = false;
      this.showKeyword = !this.showKeyword

      let url = '/api/books/top10';
      this.$Axios.get(url)
      .then(res => {
          this.top10 = res.data;
      });
    },
    closeView(){
      this.showHistory = false;
      this.showKeyword = false;
    }
  },
  created(){
    document.onclick = this.closeView;
    document.body.style.backgroundColor = "#FFFFFF";
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body{
  background-color: #FFFFFF;
}
.books-index{
    position: relative;
}
.books-index-header{
    position:fixed;
    top:0;
    left: 0;
    display : flex;
    flex-direction: column;
    width:100%;
    height:120px;
    align-content: center;
    justify-content: flex-start;
    text-align: center;
    z-index: 1;
    background-color: #f3db00;
}
.books-index-header>h4{
    font-weight: bold;
    letter-spacing: 2px;
    font-family: 'Nanum Gothic', sans-serif;
}
.books-index-header>img{
  position: absolute;
  top:0;
  right:0;
  width : 24px;
  height : 24px;
  padding : 6px;
  
}

.search-box{
  width: 60%;
  max-width: 240px;
  position: absolute;
  left: 50%;
  -webkit-transform: translateX(-50%);
  transform: translateX(-50%);
  top: 68px;
  z-index: 1;
}
.search-input{
  width:100%;
  position: relative;
  border : 1px solid #eaca01;
  background-color: rgba(255, 255, 255, 0.2);
}
.search-input>input[type=text]{
  background: none;
  border: none;
  width: 100%;
  padding: 8px 48px;
  font-size: 13px;
}
.search-input>img{
  position: absolute;
  top: 0;
  height: 100%;
  outline: none;
  padding: 8px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 4px;
}
.search-input>img:nth-of-type(1){
  right: 0;
}
.search-input>img:nth-of-type(2){
  left: 0;
}

.search-history{
  padding: 0;
  margin: 0 0 0 0;
  width: 100%;
  list-style: none;
  
  overflow: hidden;
  background-color: #FFFFFFFF;
  border: 1px solid #eaca01;
  border-top: none;
  max-height: 400px;
  overflow: auto;
}

.search-history>li{
  background-color: rgba(255, 255, 255, 0.2);
  margin-bottom : 1px;
}
.search-history>li:last-of-type{
  border-bottom : none;
}
.search-history>li>label{
  padding: 4px 16px;
  display: grid;
  font-size: 13px;
  text-align: left;
  cursor: pointer;
  /* vertical-align: middle; */
  align-items: center;
  /* justify-content: flex-start; */
  grid-template-columns: 48px 1fr 0.2fr;
}
.search-history>li>label>img{
  height: 32px;
  margin-right: 16px;
}
.search-history>li:nth-of-type(n+4)>label>span:first-of-type{
  grid-column-start:1;
  grid-column-end : 3;
}
.search-history>li>label>span:last-of-type{
  float: right;
  color: #5d5d5d;
  font-weight: bold;
  text-align: right;
}
.search-history>li>input[type=radio]{
  display: none;
}
.noimg>li>label{
  grid-template-columns: 1fr 0.4fr;
}
.noimg>li>label>span:first-of-type{
  grid-column-start : 1 !important;
  grid-column-end : 2 !important;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .1s;
}
.fade-enter, .fade-leave-to{
  opacity: 0;
}


.books-index-section{  
    position: fixed;
    top: 120px;
    left: 0;
    width: 100%;
    bottom: 34px;
    overflow: auto;
}
.books-result{
  list-style: none;
  padding: 6px;
  margin: 0;
  background-color: #fff;
  /* min-height:100%; */
}
.books-result>li{
  display: grid;
  grid-template-rows : 80px;
  grid-template-columns: 80px 1fr minmax(64px, 0.4fr);
  font-size : 14px;
  grid-column-gap: 14px;
  padding: 8px 0;
  border-bottom: 1px solid #DDDDDD;
  cursor: pointer;
}
.books-result>li:last-of-type{
  border : none;
}
.books-result>li>div{
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}
.books-result>li>div:nth-of-type(1){
  grid-column-start : 1;
  grid-column-end : 2;
  max-height: 80px;
  padding: 8px;
}
.books-result>li>div:nth-of-type(1)>img{
    max-height: 84px;
}
.books-result>li>div:nth-of-type(2){
  grid-column-start :2;
  grid-column-end : 3;
}
.books-result>li>div:nth-of-type(2)>span:first-of-type{
    color: #3B3B3B;
    font-weight: bold;
    overflow: auto;
}
.books-result>li>div:nth-of-type(2)>span:nth-of-type(n+2){
    font-size:13px;
    color: #555555;
    font-weight: 500;
}
.books-result>li>div:nth-of-type(3){
  grid-column-start :3;
  grid-column-end : 4;
}
.books-result>li>div:nth-of-type(3)>span{
  text-align: center;
}
.books-result-page{
  height: 24px;
  list-style: none;
  padding: 0;
  margin: 0;
      width: 50%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.books-result-page>li{
  display: inline-block;
}
.sale{
    font-size: 13px;
    background-color: #f3db00;
    padding: 4px 0;
}
.result-empty{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  white-space: nowrap;
}
.result-empty>img{
      width: 120px;
    height: 120px;
}
.result-empty>div{
  font-size:13px;
}
.books-index-footer{
  position:fixed;
  bottom:0;
  width:100%;
  background-color: #f3db00;
  left: 0;
}
.books-index-footer>div{
  width: 100%;
  margin: 0 !important;
  padding: 8px !important;

}

.books-detail{
  width:100%;
  height:100%;
  background-color : rgba(0,0,0,0.8);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
}
.books-detail-section{
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  background-color: #FFFFFF;
  width: 80%;
  height: 90%;
  display: grid;
  grid-template-rows: 1fr minmax(100px, 1fr)
}
.books-detail-section>div:first-of-type{
  background: #f3db00;
  text-align: center;
  padding: 12px 8px;
}
.books-detail-section>div:first-of-type>img{
  max-height: 120px;
  box-shadow: 1px 1px 1px #1d1d1d;
}    
.books-detail-section>div:first-of-type>div>div:first-of-type{
  color: #3b3b3b;
  font-weight: 700;
  padding: 8px 0;
}
.books-detail-section>div:first-of-type>div>div:nth-of-type(n+2){
  font-size: 13px;
  grid-template-columns: 60px 1fr;
  display: grid;
  text-align: left;
}
.books-detail-section>div:first-of-type>div>div:nth-of-type(n+2)>span:nth-of-type(2n-1){
  color: #3b3b3b;
  font-weight: 700;
  text-align: right;
  margin-right: 12px;
}

.books-detail-section>div:first-of-type>div>div:last-of-type{
    grid-template-columns: 60px 1fr 60px 1fr;
}

.books-detail-section>div:last-of-type{
  overflow: auto;
  padding: 12px 8px;
}
.books-detail-section>div:last-of-type>h4{
  margin : 4px 0;
}
.books-detail-section>div:last-of-type>div{
  font-size: 14px;

}
.close-button {
    position: absolute;
    right: 0;
    top: 0;
    width: 32px;
    height: 32px;
    opacity: .3;
    cursor: pointer;
}
.close-button:hover {
  opacity: 1;
}
.close-button:before, .close-button:after {
  position: absolute;
  right: 16px;
  content: " ";
  height: 18px;
  width: 2px;
  background-color: #333;
  top: 8px;
}
.close-button:before {
  transform: rotate(45deg);
}
.close-button:after {
  transform: rotate(-45deg);
}
</style>
<style>
.vue-ads-flex{
  justify-content: center !important;
  align-content: center !important;
}
.vue-ads-bg-teal-500 {
  background-color: #1d1d1d !important;
}
</style>
