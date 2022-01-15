<template>
<div @scroll="getArticle" class="router-wrapper">
    <div class="router-wrapper2">
        <div v-for="(item, index) in this.boardList" :key="index">
          <div class="null-content" v-if="item.isNull">{{item.content}}</div>
          <div v-else>
          <div class="board" v-if="item.delAt === 'N'">
            <div class="name-div">
                <div>
                    <div>{{item.member.memNick}}</div>
                    <div>{{item.boardDate}}</div>
                </div>
                <!-- 이 부분에다가 v-if로 토큰값 비교해서 작성자일 경우 수정,삭제 버튼.. 아닐경우 신고 버튼-->
                <div class="icon-container" v-if="item.수정했니 === false">
                  <div v-if="item.fileAt === 'Y'">
                    <button @click="downloadFile(item)" id="file-btn">첨부파일: {{ item.boardFileDTO.fileName }}</button>
                  </div>
                </div>
                <div id="finish-div" v-if="item.수정했니 === true"
                                    @click="exportFinish(item); increasingIsExportUpdate()">Finish
                </div>
            </div>
            <div class="content-div no-read-only" v-if="item.isModify == true">
                {{ item.boardCn }}
            </div>

            <div class="content-div read-only" v-if="item.isModify == false">
                <editor :originContent="item" :isExport="isExport" @exportContent="getContent" class="content-div"/>
            </div>
            <div id="btn-div">
                <div>
                    <button @click="getCommentList(item)" class="comment-btn">댓글 {{ item.totalComments }}개</button>
                </div>
            </div>
            <div class="comment-wrapper">
                <input class="comment-input" type="text" placeholder="댓글을 입력하세요">
                <button id="button-id" class="comment-btn" @click="insertComment(item)">등록</button>
            </div>
            <BoardComment :board="item"/>
          </div>
        </div>
        <span id="goback">
            <button id="goback-btn" @click="backToFirst">처음으로</button>
        </span>
        </div>
    </div>
</div>
</template>

<script>
import BoardComment from '@/components/component/noAccess/Community/BoardComment.vue'
import {  mapActions, mapMutations, mapState } from 'vuex'
import editor from '../../global/editor.vue'

export default {
    name : 'qna',

    data(){
        return {
            updateContent : '',
            axiosState : false,
            isUpdate : false,
            isExport : 0,
            isReportClick : false,
            likeToggle : false,
            isBoardNull: false
        }
    },
    computed : {
        ...mapState({
            boardList : state=>state.community.boardList,
            updateCheck : state => state.community.updateCheck,
            numberOfArticle : state => state.community.numberOfArticle,
            articlesOnView : state => state.community.articlesOnView,
            axiosState: state => state.community.axiosState
        })
    },

    methods: {
        ...mapActions({
            getBoardList : 'community/getBoardList',
            getBoardNum : 'community/getBoardNum',
            getMoreList : 'community/getMoreList',
            getComments : 'community/getComments'
        }),
        ...mapMutations({
            changeIsUpdate : 'community/changeIsUpdate',
            changeBoardIsModify : 'community/changeBoardIsModify',
            changeUpdateCheck : 'community/changeUpdateCheck',
            setAxiosState: 'community/setAxiosState',
            deleteBoards : 'community/deleteBoards'
        }),

        downloadFile(item) {
          const url = '/downloadFile'
          const boardIdx = item.boardIdx
          console.log(boardIdx)
          const memIdx = item.member.memIdx
          const fileName = item.boardFileDTO.fileName
          const codeDetail = item.codeDetail.codeDetailIdx
          console.log(fileName)
          console.log(memIdx)

          this.axios({
            url: url,
            method: 'post',
            responseType: 'blob',
            data: {
              boardIdx: boardIdx,
              memIdx: memIdx,
              fileName: fileName,
              codeDetail: codeDetail
            }
          }).then(e => {
            const url = window.URL.createObjectURL(new Blob([e.data]));
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', fileName)
            document.body.appendChild(link)
            link.click()
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
            if(this.articlesOnView === this.numberOfArticle) {
                return
            }
            const fullScroll = e.target.scrollHeight
            const nowScroll = e.target.scrollTop
            const position = this.$route.fullPath.split('/')[2]

          if(this.articlesOnView <= 4) {
            this.getMoreList(position)
          } else {
            if((fullScroll - nowScroll) < (fullScroll / 1.5) && !this.axiosState) {
              this.getMoreList(position)
            }
          }
        },

        getCommentList(item) {
            if(item.totalComments <= 0) {
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
        },
        backToFirst(){
          document.querySelector('.router-wrapper').scroll(0,0)
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

    components : {
        BoardComment,
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
    height: fit-content;
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

img {
    width: 15px;
    height: 15px;
    cursor: pointer;
}

#btn-div{
    font-size: 14px;
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

#goback {
  border-radius: 10px;
  background-color: coral;
  color: #fff;
  position: absolute;
  bottom: 10px;
  right: 13vw;
  z-index: 9999;
}

#goback-btn {
  color: #fff;
  padding: 5px;
}

.null-content {
  font-size: 20px;
  color: #fff;
}
</style>