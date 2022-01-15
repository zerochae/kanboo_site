<template>
<div @scroll="getArticle" class="router-wrapper">
    <div class="router-wrapper2">
        <div v-for="(item, index) in this.boardList" :key="index">
            <div class="null-content" v-if="item.isNull">{{item.content}}</div>
            <div v-else>
            <div class="board" v-if="item.delAt==='N'">
            <div class="name-div">
                <div>
                    <div>{{item.member.memNick}}</div>
                    <div>{{item.boardDate.substring(0, 19).replace("T", " ")}}</div>
                </div>
                <!-- 이 부분에다가 v-if로 토큰값 비교해서 작성자일 경우 수정,삭제 버튼.. 아닐경우 신고 버튼-->
                <div class="icon-container" v-if="item.수정했니 === false">
                    <div v-if="item.fileAt === 'Y'">
                      <button @click="downloadFile(item)" id="file-btn" v-text="`첨부파일: ${item.boardFileDTO.fileName}`"></button>
                    </div>
                    <div class="icon-div">
                        <i @click="this.changeBoardIsModify(item);
                                    this.changeIsUpdate(item);"
                                    class="fas fa-edit"
                            v-if="this.updateCheck == false"></i>
                    </div>
                    <div class="icon-div">
                        <i @click="confirmDelete(item)" class="far fa-trash-alt"></i>
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
                                    @click='increasingIsExportUpdate()'
                                    >Finish
                </div>
            </div>
            <div class="content-div no-read-only" v-if="item.isModify == true" v-html="item.boardCn">
            </div>

            <div class="content-div read-only" v-if="item.isModify == false">
                <editor :boardIdx="item.boardIdx" :originContent="item" :isExport="isExport" @exportContent="getContent" class="content-div"/>
            </div>
            <div id="btn-div">
                  <div class="like-div" v-if="!item.like">
                    <div @click="like(item)" class="heart unclick"></div>
                    <span>{{ item.totalLikes }} 개</span>
                  </div>
                  <div @click="like(item)" class="like-div" v-else>
                    <div class="heart clicked"></div>
                    <span>{{ item.totalLikes }} 개</span>
                  </div>
                <div>
                    <button @click="getCommentList(item)" class="comment-btn">댓글 {{ item.totalComments }}개</button>
                </div>
            </div>
            <div class="comment-wrapper">
                <input class="comment-input" v-model="item.insertComment" type="text" placeholder="댓글을 입력하세요">
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
    name : 'Free',

    data(){
        return {
            updateContent : '',
            isUpdate : false,
            isExport : 0,
            isReportClick : false,
            likeToggle : false,
            isBoardNull: false,
            category : 7,
        }
    },
    computed : {
        ...mapState({
            boardList : state => state.community.boardList,
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
            getComments : 'community/getComments',
        }),

        ...mapMutations({
            changeIsUpdate : 'community/changeIsUpdate',
            changeBoardIsModify : 'community/changeBoardIsModify',
            changeUpdateCheck : 'community/changeUpdateCheck',
            setAxiosState: 'community/setAxiosState',
            deleteBoards : 'community/deleteBoards',
            changeIsLike : 'community/changeIsLike',
            increaseNumOfArticleAfterInsert : 'community/increaseNumOfArticleAfterInsert'
        }),

        convertFileSize(fileSize){
          let str
          //MB 단위 이상일때 MB 단위로 환산
          if (fileSize >= 1024 * 1024) {
            fileSize = fileSize / (1024 * 1024);
            str = fileSize + ' MB';
          }
          //KB 단위 이상일때 KB 단위로 환산
          else if (fileSize >= 1024) {
            fileSize = fileSize / 1024;
            str = fileSize + ' KB';
          }
          //KB 단위보다 작을때 byte 단위로 환산
          else {
            str = fileSize + ' byte';
          }
          return str;
        },

        getContent(e) {
          e.originContent.boardCn = e._data
          const modifiedFile = e._file
          e.originContent.수정했니 = false
          e.originContent.isModify = true

          let formData = new FormData()
          let boardIdxToInsertFile = 0
          let fileAt = 'N'
          if(e._file !== ''){
            fileAt = 'Y'
          }
          this.axios({
            method: 'post',
            url: '/updateBoard',
            data: {
              boardCn: e._data,
              boardIdx : e._boardIdx,
              fileAt : fileAt,
              token: "#1921"
            }
          }).then(ele =>{
            boardIdxToInsertFile = ele.data.boardIdx
            e.originContent.fileAt = ele.data.fileAt
            this.increaseNumOfArticleAfterInsert()
            if(e._file !== ''){
              this.makeFormData(modifiedFile, formData, boardIdxToInsertFile)
            }

            if(e._file !== ''){
              this.axios.post("/insertFile", formData,
                  { headers: { 'Content-Type': 'multipart/form-data' } })
                  .then(res => {
                    e.originContent.boardFileDTO = res.data
                  })
            }
          })
        },

        makeFormData(ele, obj, boardIdxToInsertFile) {
          const fileSize = this.convertFileSize(ele.size)
          const fileNameAndExtension = ele.name.lastIndexOf('.')
          const fileName = ele.name
          const extension = ele.name.substring( fileNameAndExtension, ele.name.length)

          obj.append('file', ele)
          obj.append('fileSize', fileSize)
          obj.append('fileName', fileName)
          obj.append('extensionName', extension)
          obj.append('boardIdxToInsertFile', boardIdxToInsertFile)
          obj.append('category', this.category)
          obj.append("checkInsertOrUpdate", "update")
        },

        downloadFile(item){
            const url = '/downloadFile'
            const boardIdx = item.boardIdx
            const memIdx = item.member.memIdx
            const fileName = item.boardFileDTO.fileName
            const codeDetail = item.codeDetail.codeDetailIdx

          this.axios({
              url : url,
              method : 'post',
              responseType : 'blob',
              data : {
                boardIdx : boardIdx,
                memIdx : memIdx,
                fileName : fileName,
                codeDetail : codeDetail
            }
          }).then(e => {
              const url = window.URL.createObjectURL(new Blob([e.data]));
              const link = document.createElement('a')
              link.href = url
              link.setAttribute('download',fileName)
              document.body.appendChild(link)
              link.click()
          })
        },

        setLikeFlag(){
            this.likeToggle = !this.likeToggle
        },

        changeLikeColor(item){
          if(item.like === true){
            this.cancelLike(item)
          } else {
            this.updateLike(item)
          }
          item.like = !item.like
        },

        like(item){
          this.changeLikeColor(item)
        },

        updateLike(item){
            console.log('좋아요 추가')
            this.axios.get('/updateLike', { params : { boardIdx : item.boardIdx, token : '#1921' }})
                        .then(e =>  {
                            if(e !== null){
                                    item.totalLikes += 1
                            }
                        })
        },
        cancelLike(item){
          console.log('좋아요 삭제')
          this.axios.get('/deleteLike', { params : { boardIdx : item.boardIdx, token : '#1921' }})
              .then(e =>  {
                if(e !== null){
                  item.totalLikes -= 1
                }
              }).catch(() => {
                item.like = false
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
            console.log('============')
            console.log(item)
            console.log('============')
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

        confirmDelete(item){
          if (confirm("해당 게시글을 정말 삭제하시겠습니까?")){
            this.deleteBoard(item)
          }
        },

        //게시판 삭제
        // token : sessionStorage.getItem('token')
        deleteBoard(item){
            this.axios
                .get('/DeleteBoard',{
                          params : {
                                    boardIdx : item.boardIdx,
                          }})
                .then(e =>{
                  if(e.data === true){
                    item.delAt = 'Y'
                    this.deleteBoards()
                  } else {
                    alert('삭제를 실패했습니다.')
                  }
                })
        },
        getCommentList(item) {
            if(item.totalComments <= 0) {
                return
            }
            this.getComments(item)
        },

        insertComment(item){
          console.log(item)
          console.log(item.insertComment)
          this.axios.post('/insertComment',  {
                            answerCn : item.insertComment,
                            answerDate : '',
                            answerDelAt : 'N',
                            boardIdx : item.boardIdx,
                            token : '#1921'
            }).then(e => {
                    if(item.totalComments === 0){
                      item.totalComments += 1
                      this.addAttributeToComments(e.data)
                      item.insertComment = ''
                    }
                    if(item.commentsOnView > 0){
                      item.totalComments += 1
                      this.addAttributeToComments(e.data)
                      item.commentList.unshift(e.data)
                      item.insertComment = ''
                    }
          })
        },

        addAttributeToComments(e){
          e.isOpen = false
          e.isUpdate = false
          e.isFinish = false
          e.isModify = true
        },

        backToFirst(){
          document.querySelector('.router-wrapper').scroll(0,0)
        },
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
    
    Mounted() {
        this.getBoardNum()
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
    /*height: 300px;*/
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
    color: #fff;
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

#target {
  display: none;
}
.heart {
  width: 10px;
  height: 10px;
  position: relative;
  transform: rotate(45deg);
  cursor: pointer;

}
.heart::before,
.heart::after {
  content: "";
  width: 10px;
  height: 10px;
  position: absolute;
  border-radius: 50%;
}
.heart::before {
  left: -50%;
}
.heart::after {
  top: -50%;
}
.unclick::before,
.unclick::after,
.unclick {
  background-color: #fff;
}
.clicked::before,
.clicked::after,
.clicked {
  background-color: #aa5500;
}

.like-div {
  display: flex;
  gap: 10px;
}
</style>