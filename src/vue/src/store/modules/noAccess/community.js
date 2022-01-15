/*
    검색버튼 관력 초기화(조건 잡아주세용~~)
    select box 변경시 키워드가 입력되있으면 바로 검색
    키워드가 공백(trim잡아주셔야 됩니다)이면 데이터 초기화 후 조건 없이 글 가져오기
*/
//import { createStore } from 'vuex'
import axios from 'axios'

const community = {
    namespaced: true,

    state: {
        boardList: [],
        numberOfArticle: 0,
        articlesOnView: 0,
        content: '',
        updateCheck: false,
        selected: 'All',
        key: '',
        codeDetail : 7,
        axiosState : false,
        isOpen : false,
    },

    mutations: {
        changeWriteIsOpen(state){
            state.isOpen = !state.isOpen
        },

        changeCodeDetail(state, payload){
            state.codeDetail = payload
        },

        pushToBoardList(state, payload) {
            payload["열렸니"] = false
            payload["수정했니"] = false
            payload["commentList"] = []
            payload["commentsOnView"] = 0
            payload.isModify = true
            payload.insertComment = ''
            state.boardList.push(payload)
        },

        unshiftToBoardList(state, payload){
            payload["열렸니"] = false
            payload["수정했니"] = false
            payload["commentList"] = []
            payload["commentsOnView"] = 0
            payload.isModify = true
            payload.insertComment = ''
            state.boardList.unshift(payload)
            state.articlesOnView++
        },

        unshiftToCommentList(state, payload){
            payload
        },

        // changeIsLike(state, payload){
        //     payload.isLike = !payload.isLike
        // },

        deleteBoards(state){
            state.articlesOnView--
            console.log('못옴??')
            console.log(state.articlesOnView)
            state.numberOfArticle--
        },

        increaseNumOfArticleAfterInsert(state){
            state.numberOfArticl++
        },

        setNumberOfArticle(state, payload) {
            state.numberOfArticle = payload
        },

        setArticlesOnView(state, payload) {
            state.articlesOnView += payload
        },

        addingToArticlesOnView(state, payload) {
            state.articlesOnView += payload
        },

        pushToComment(state, item) {
            if (item._board.commentList.length !== 0 || item._board.totalComments === 0) {
                console.log('걸리니')
                return
            }
            console.log('지나가니')
            item._board.commentList.push(...item._comment)
            item._board.commentsOnView = item._comment.length;
        },

        //---------------게시글 관련 ------------------
        changeIsOpen(state, payload) {
            payload.열렸니 = !payload.열렸니
        },

        changeIsUpdate(state, payload) {
            payload.수정했니 = !payload.수정했니
        },
        //--------------------------------------------

        // 해당 게시물 댓글 리스트에 댓글 추가
        addingToCommentList(state, payload) {
            state
            payload.board.commentList.push(...payload.commentList)
        },

        //----------------댓글 관련!!!-------------------
        changeCommentsIsOpen(state, payload) {
            payload.isOpen = !payload.isOpen
            state
        },

        changeCommentsIsUpdate(state, payload) {
            payload.isUpdate = !payload.isUpdate
            state
        },

        changeIsFinish(state, payload) {
            payload.isFinish = !payload.isFinish
            state
        },

        changeIsModify(state, payload) {
            payload.isModify = !payload.isModify
            state
        },

        //-------------------------------------------
        changeBoardIsModify(state, payload) {
            payload.isModify = !payload.isModify
            state
        },
        changeUpdateCheck(state) {
            state.updateCheck = !state.updateCheck
        },

        getSelectedAndKey(state, payload) {
            state.key = payload.key
            state.selected = payload.selected
        },

        // 카테고리 변경시 데이터 초기화
        resetData(state) {
            state.boardList = []
            state.articlesOnView = 0
        },

        boardListNullPush(state, item) {
            state.boardList.push(item)
        },

        setAxiosState(state, stat) {
            state.axiosState = stat
        },
    },

    actions: {
        getBoardList(context, position) {
            let detail
            // const _token = sessionStorage.getItem("token")
            if(position === 'qna') {
                detail = 8
            } else if(position === 'free') {
                detail = 7
            } else {
                detail = context.state.codeDetail
            }
            axios.get('/boardTest', {
                    params: {
                        selected: context.state.selected,
                        key: context.state.key,
                        articleOnvView: context.state.articlesOnView,
                        codeDetail: detail,
                        token : "#1921"
                    }
                }
            )
                .then(e => {
                    console.log("뀨??????????????????")
                    console.log(e.data)
                    if(e.data.length === 0) {
                        const obj = {
                            isNull: true,
                            content: '해당하는 게시물이 없습니다.'
                        }
                        context.commit('boardListNullPush', obj)
                        return
                    }
                    for (let item of e.data) {
                        context.commit('pushToBoardList', item)
                    }
                    context.commit('setArticlesOnView', e.data.length)
                })
        },

        getBoardNum(context, position) {
            let detail
            if(position === 'qna') {
                detail = 8
            } else if(position === 'free') {
                detail = 7
            } else {
                detail = context.state.codeDetail
            }
            axios.get('/getArticleNum', {
                    params : {
                        key : context.state.key,
                        selected : context.state.selected,
                        codeDetails : detail
                    }
                }
            )
                .then(e => {
                    console.log('---게시글 총 글 수---')
                    console.log(e.data)
                    console.log('------')
                    context.commit('setNumberOfArticle', e.data)
                })
        },

        getMoreList(context, position) {
            let detail
            if(position === 'qna') {
                detail = 8
            } else if(position === 'free') {
                detail = 7
            } else {
                detail = context.state.codeDetail
            }

            if(context.state.axiosState) {
                return
            }
            context.commit('setAxiosState', true)
            axios.get('/boardTest',
                {
                    params: {
                        selected: context.state.selected,
                        key: context.state.key,
                        articleOnvView: context.state.articlesOnView,
                        codeDetail: detail,
                        token : "#1921",
                    }
                })
                .then(e => {
                    for (let item of e.data) {
                        context.commit('pushToBoardList', item)
                    }
                    context.commit('setArticlesOnView', e.data.length)
                    context.commit('setAxiosState', false)
                })
        },

        //컨트롤러에서 Comment찾아서 { params : {idx : item.idx, number : this.commentsOnView }}
        //commentsOnView는 여기서 params로 0을 넘겨줌
        //context.state.commentsOnView 이렇게 매개변수  넘겨줌
        getComments(context, item) {
            axios.get('/BoardComment', { params : {
                    boardIdx : item.boardIdx,
                    commentsOnView : item.commentsOnView,
                }})
                .then(e => {
                    console.log('댓글 가져오니 ?')
                    console.log(e)
                    context.commit('changeIsOpen', item)

                    for (let item of e.data) {
                        item.isOpen = false
                        item.isUpdate = false
                        item.isFinish = false
                        item.isModify = true
                    }
                    console.log('======댓글가져오고나서 데이터=====')
                    console.log(e.data)
                    console.log('===========')
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
            axios.get('/BoardComment', { params : {
                    boardIdx : item.boardIdx,
                    commentsOnView : item.commentsOnView,
                }})
                .then(e => {
                    for (let item of e.data) {
                        item.isOpen = false
                        item.isUpdate = false
                        item.isFinish = false
                        item.isModify = true
                    }
                    item.commentsOnView += e.data.length;
                    const payload = {
                        board: item,
                        commentList: e.data
                    }
                    //가져온 데이터 뮤테이션으로 바꿔주기
                    context.commit('addingToCommentList', payload)
                })
        }
    }
}

export default community