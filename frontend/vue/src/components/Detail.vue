<template>
  <div class="books-detail">
    <header class="books-detail-header">
        <h4>BOOK'S FINDER</h4>
        <div>
          <input type="text" v-model="query" v-on:keyup.enter="search">
          <button v-on:click="search">검색</button>
        </div>
        <div></div>
    </header>
    <section class="books-detail-section">
        <div>
          <img :src="this.props.book.thumbnail">
          <div>
            <div>{{book.title}}</div>
            <div>
              <span>출판</span>
              <span>{{book.publisher}}</span>
              <span>{{book.datetime}}</span>
            </div>
            <div>
              <span></span>
              <span>{{book.authors}}</span>
            </div>

            <div>
              <span>{{book.price}}</span>
              <span>{{book.sale_price}}</span>
              <span>{{book.isbn}}</span>
            </div>
          </div>
        </div>
        <div>
          <h4>소개</h4>
          <div>
            {{book.contents}}
          </div>
        </div>
    </section>
  </div>
</template>
<script>
export default {
  name: 'Detail',
  data () {
    return {
      // book : this.$router.params
    }
  },
  methods: {
    search (page) {
        
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.books-index{
    position: relative;
    width:100%;
    height:100%;
}
.books-index-header{
    display : flex;
    flex-direction: column;
    width:100%;
    height:120px;
    align-content: center;
    justify-content: flex-start;
    text-align: center;
}
.books-index-header>h4{
    font-weight: bold;
    letter-spacing: 2px;
    font-family: 'Nanum Gothic', sans-serif;
}
.books-index-header>div{
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    position: relative;
}
.books-index-header>div>input[type=text]{
    background-color: hsla(0,0%,100%,0.2);
    border-radius: 4px;
    border: 1px solid #EACA01;
    padding: 12px;
    width: 50%;
    max-width: 240px;
}
.books-index-header>div>button{
    border: 1px solid #eaca01;
    background: rgba(255, 255, 255, 0.7);
    outline: none;
    padding: 12px 0;
}
.books-index-section{
    width:100%;
    height : calc(100% - 120px);
}
.books-result{
  list-style: none;
  padding: 12px 16px;
  margin:0;
  background-color: #FFFFFF;
  height: calc(100% - 36px);
  overflow: auto;
}
.books-result>li{
  display: grid;
  grid-template-rows : 80px;
  grid-template-columns: 80px 1fr minmax(64px, 0.4fr);
  font-size : 14px;
  grid-column-gap: 14px;
  padding: 8px 0;
  border-bottom: 1px solid #DDDDDD;
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
</style>