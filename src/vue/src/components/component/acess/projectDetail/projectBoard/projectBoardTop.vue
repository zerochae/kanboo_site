<template>
<div>
<div @scroll="getArticle" class="router-wrapper">
    <div class="router-wrapper2">
        <div class="board" v-for="(item, index) in this.boardList" :key="index">
            <div class="name-div">
                <div>
                    <div>{{item.name}}</div>
                    <div>{{item.date}}</div>
                </div>
                <!-- 이 부분에다가 v-if로 토큰값 비교해서 작성자일 경우 수정,삭제 버튼.. 아닐경우 신고 버튼-->
                <div class="icon-container" v-if="item.수정했니 === false">
                    <div class="icon-div">
                        <i @click="this.changeBoardIsModify(item); 
                                    this.changeIsUpdate(item);"
                                    class="fas fa-edit"
                            v-if="this.updateCheck == false"></i>
                    </div>
                    <div class="icon-div">
                        <i @click="deleteBoard(item)" class="far fa-trash-alt"></i>
                    </div>
                    <!-- 밑의 div에다가 update axios를 하는 메소드 이름을 @click에다가 추가-->
                </div>
                <div class="report-div">
                    <span @click="report(item); changeIsReportClick()" v-if="isReportClick === false">
                        <img class="no-report" src="@/assets/noneReport.png">
                    </span>
                    <span @click="cancelReport(item); changeIsReportClick()" v-if="isReportClick === true">
                        <img class="report" src="@/assets/report.png">
                    </span>
                </div>
                <div id="finish-div" v-if="item.수정했니 === true"
                                    @click="exportFinish(item); increasingIsExportUpdate()">Finish
                </div>
            </div>
            <div class="content-div no-read-only" v-if="item.isModify == true">
                {{ item.content }}
            </div>

            <div class="content-div read-only" v-if="item.isModify == false">
                <editor :originContent="item" :isExport="isExport" @exportContent="getContent" class="content-div"/>
            </div>
            <div id="btn-div">
                <div @click="like(item); setLikeFlag()" v-if="!likeToggle">
                    <i class="far fa-thumbs-up"></i>
                    <span>{{ item.좋아요 }} 개</span> 
                </div>
                <div class="clickedThumbs-up" @click="cancelLike(item); setLikeFlag()" v-else>
                    <i class="far fa-thumbs-up"></i>
                    <span>{{ item.좋아요 }} 개</span> 
                </div>
                <div>
                    <button @click="getCommentList(item)" class="comment-btn">댓글 {{ item.댓글수 }}개</button>
                </div>
            </div>
            <div class="comment-wrapper">
                <input class="comment-input" typ1e="text" placeholder="댓글을 입력하세요">
                <button id="button-id" class="comment-btn" @click="insertComment(item)">등록</button>
            </div>
            <ProjectBoardComment :board="item"/>
        </div>
    </div>
</div>
</div>
</template>

<script>
import {  mapActions, mapMutations, mapState } from 'vuex'
import editor from '../../../global/editor.vue'
import ProjectBoardComment from './ProjectBoardComment.vue'

export default {
    name : 'Free',

    data(){
        return {
            updateContent : '',
            axiosState : false,
            //총 게시글 수
            numberOfArticle : 0,
            //보여지는 게시글 수
            articlesOnView : 0,
            isUpdate : false,
            isExport : 0,
            isReportClick : false,
            likeToggle : false,
        }
    },
    computed : {
        ...mapState({
            boardList : state=>state.projectBoard.boardList,
            updateCheck : state => state.projectBoard.updateCheck,
        })
    },

    methods: {
        ...mapActions({
            getBoardList : 'projectBoard/getBoardList',
            getBoardNum : 'projectBoard/getBoardNum',
            getMoreList : 'projectBoard/getMoreList',
            getComments : 'projectBoard/getComments',
        }),
        ...mapMutations({
            changeIsUpdate : 'projectBoard/changeIsUpdate',
            changeBoardIsModify : 'projectBoard/changeBoardIsModify',
            changeUpdateCheck : 'projectBoard/changeUpdateCheck',
        }),

        setLikeFlag(){
            this.likeToggle = !this.likeToggle
        },

        like(item){
            this.axios.post('url',null, { params : { idx : item.idx }})
                        .then(e => {
                            if(e > 0){
                                if(!this.likeToggle){
                                    item.좋아요 += 1
                                }
                            }
                        })
        },

        cancelLike(item){
            this.axios.post('url',null, { params : { idx : item.idx }})
                        .then(e =>  {
                            if(e > 0){
                                if(!this.likeToggle){
                                    item.좋아요 -= 1
                                }
                            }
                        })
        },

        changeIsReportClick() {
            this.isReportClick = !this.isReportClick
        },
        
        report(item){
            this.axios.get('url', null, { params : {idx : item.idx}})
                        .then(e => {
                            console.log(e)
                        })
        },
        
        cancelReport(item){
            
            this.axios.get('url', null, { params : {idx : item.idx}})
                        .then(e => {
                            console.log(e)
                        })
        },

        exportFinish(item) {
            this.changeIsUpdate(item); 
            this.changeBoardIsModify(item);
        },

        increasingIsExportUpdate(){
            this.isExport++
        },

        getArticle(e){  
            // if(this.articlesOnView === this.numberOfArticle) {
            //     return
            // }

            const fullSroll = e.target.scrollHeight
            const nowScroll = e.target.scrollTop

            if((fullSroll - nowScroll) < (fullSroll / 1.5) && !this.axiosState) {
                //원래는 이 부분에서 현재보여지는 게시글의 개수인 articlesOnView 같이 넘김
                //Controller에서 보여지는 개시글의 개수를 받아서 jpa문법으로 페이징처리를 위함
                //params : {articleNum : this.articleOnView}
                    this.getMoreList()
            }
        },
        //게시판 삭제
        deleteBoard(item){
            this.axios
                .delete('', null, {params : {
                                    board : item,
                                    token : sessionStorage.getItem('token')}})
                .then(() =>{})
                .catch(() => {})
        },
        //게시판 수정
        updateBoard(item){
            this.axios
                .put('',null, {params : {board : item,
                                content : this.updateContent,
                                token : sessionStorage.getItem('token')}})
                .then(() => {});
        },
        getCommentList(item) {
            if(item.댓글수 <= 0) {
                return
            }
            this.getComments(item)
        },
        //댓글 등록
        insertComment(item){
            const commentContent = document.querySelector('.comment-input')
            this.axios.post('url', null, { params :
                                            { idx : item.idx, commentContent : commentContent.value } })
              .then(() => {
                  commentContent.value = ''
              })
        }

    },

    watch:{
        isExport(){
            let editor = document.querySelector('#content')
            let multipleFiles = document.querySelector('#multipleFiles')
                if(editor){
                    let _data = editor.innerHTML
                    let _files = multipleFiles.files
                    console.log(_data)
                    console.log(_files)
                }
        }
    },
    
    mounted() {
        this.getBoardList()
        this.getBoardNum()
    },

    components : {
        ProjectBoardComment,
        editor,
    },
}
</script>

<style scoped>
.board {
    width: 60vw;
    height: 80%;
    background-color: #2C2F3B;
    margin: 22px auto ;
    border-radius: 15px;
    color:white;
    padding: 30px;
}

.name-div {
    display: flex;
    justify-content: space-between;
    padding-top: 10px;
    padding-left: 20px;
    padding-right: 20px;
}

.icon-div {
    cursor: pointer;
}

.icon-container {
    display: flex;
    gap: 18px;
}

.content-div {
    height: 300px;
    color: white;
    width: 100%;
}

.no-read-only {
    padding: 20px;
}

.read-only {
    margin-top: 20px;
    height: 100%;
}

.router-wrapper {
    overflow: scroll;
    height: calc(100vh - 100px);
   -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}

.router-wrapper::-webkit-scrollbar {
    display: none;
}

#finish-div {
    color: white;
    font-weight: bold;
    cursor : pointer;
}

.comment-btn {
    color : black;

}

.comment-input {
    border-radius: 20px;
    background-color: #414556;
    height: 20px;
    color: #FFFFFF;
    padding-left : 14px;
    width: 100%;
    margin-left: 8px;
    outline: none;
    border: none;
    padding: 20px;
}

#btn-div {
    display: flex;
    justify-content: right;
    margin-bottom: 10px;
}

.comment-btn {
    background-color: #2C2F3B;
    border-radius: 10px;
    font-size: 13px;
    color: #fff;
    width: 70px;
}

#comment-insert-div {
    display: flex;
    justify-content: right;
    margin-top: 10px;
}

img {
    width: 15px;
    height: 15px;
    cursor: pointer;
}

.report-div {
    display: none;
}

#btn-div{
    font-size: 14px;
}

#btn-div > div:first-child{
    padding-right: 30px;
}

#btn-div > div > i{
    padding-right: 10px;
}

.clickedThumbs-up > i{
    color: #FF8906;
}

.comment-wrapper {
    display: flex;
    width: 100%;
}

#button-id {
    background: #FF8906;
    margin-left: 10px;
    height: 40px;
}
</style>