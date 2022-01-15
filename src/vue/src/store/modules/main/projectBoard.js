//import { createStore } from 'vuex'
import axios from 'axios'

const community = {
  namespaced: true,

  state: {
    boardList : [],
    numberOfArticle : 0,
    articlesOnView : 0,
    content : '',
    updateCheck : false,
    selected : 'All',
    key : '',
  },
  
  mutations: {
    pushToBoardList(state, payload){
      payload["열렸니"] = false
      payload["수정했니"] = false
      payload.isModify = true
      state.boardList.push(payload)
    },

    setNumberOfArticle(state, payload){
      state.numberOfArticle = payload
    },

    setArticlesOnView(state){
      state.articlesOnView = state.boardList.length
    },

    addingToArticlesOnView(state, payload) {
      state.articlesOnView += payload
    },

    pushToComment(state, item) {
      if(item._board.댓글.length !== 0 || item._board.댓글수 === 0){
        return
      }

      item._board.댓글.push(...item._comment)
      item._board.commentsOnView = item._comment.length;
    },

    //---------------게시글 관련 ------------------
    changeIsOpen(state, payload){
      payload.열렸니 = !payload.열렸니
      state
    },

    changeIsUpdate(state, payload){
      payload.수정했니 = !payload.수정했니
      state
    },
    //--------------------------------------------

    // 해당 게시물 댓글 리스트에 댓글 추가
    addingToCommentList(state, payload) {
      state
      payload.board.댓글.push(...payload.commentList)
    },

    //----------------댓글 관련!!!-------------------
    changeCommentsIsOpen(state, payload){
      payload.isOpen = !payload.isOpen
      state
    },

    changeCommentsIsUpdate(state, payload){
      payload.isUpdate = !payload.isUpdate
      state
    },

    changeIsFinish(state, payload){
      payload.isFinish = !payload.isFinish
      state
    },

    changeIsModify(state, payload){
      payload.isModify = !payload.isModify
      state
    },

    //-------------------------------------------
    changeBoardIsModify(state, payload){
      payload.isModify = !payload.isModify
      state
    },
    changeUpdateCheck(state){
      state.updateCheck = !state.updateCheck
    },

    getSelectedAndKey(state, payload){
      state.key = payload.key
      state.selected = payload.selected
    },

    emptyBoardList(state){
      state.boardList = []
    }
  },
  
  actions: {
    //articleOnView state에 ?
    getBoardList(context){
      // var object = {
      //   "selected" : selected,
      //   "key" : key
      // }

      axios.get('/BoardList.json')
                .then(e => {
                  for(let item of e.data){
                    context.commit('pushToBoardList', item)
                  } 
                  context.commit('setArticlesOnView')
                })
    },

    getBoardNum(context){
      axios.get('/articleNum.json')
                .then(e => {
                context.commit('setNumberOfArticle', e.data.articleNum)
                })
    },
    //원래는 이 부분에서 현재보여지는 게시글의 개수인 articleOnView를 같이 넘김
    //Controller에서 보여지는 개시글의 개수를 받아서 jpa문법으로 페이징처리를 위함
    //params : {articleNum : this.articleOnView}
    getMoreList(context){
      // var object = {
      //   "selected" : selected,
      //   "key" : key
      // }
      axios.get('/BoardList.json')
                .then(e => {
                  for(let item of e.data){
                    context.commit('pushToBoardList', item)
                  }
                  context.commit('setArticlesOnView')
                  // context.articlesOnView += e.data.length
                })
    },

    //컨트롤러에서 Comment찾아서 { params : {idx : item.idx, number : this.commentsOnView }}
    //commentsOnView는 여기서 params로 0을 넘겨줌
    //context.state.commentsOnView 이렇게 매개변수  넘겨줌
    getComments(context, item) {
      axios.get('/BoardComment.json').then(e => {
        context.commit('changeIsOpen', item)
        
        for(let item of e.data){
          item.isOpen = false
          item.isUpdate = false
          item.isFinish = false
          item.isModify = true
        }
        console.log(e.data)
        const obj = {
          //해당 게시글 object
          _board: item,
          //ex) 댓글 5개 씩 가져옴 ..
          _comment: e.data
        }
        context.commit('pushToComment', obj)
      })
    },

    extraComments(context, item) {
      //엑시오스 호출
      //이 부분에에서 { params : { idx : item.idx, number : this.commentsOnView}}넘겨줌.. 12개
      axios.get('/BoardComment.json').then(e => {
        for(let item of e.data){
          item.isOpen = false
          item.isUpdate = false
          item.isFinish = false
          item.isModify = true
        }
        item.commentsOnView += e.data.length;
        const payload = {
          board: item,
          commentList : e.data
        }
        //가져온 데이터 뮤테이션으로 바꿔주기
        context.commit('addingToCommentList', payload)
      })
    }
  }
}

export default community