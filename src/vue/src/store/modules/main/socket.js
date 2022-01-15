import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import moment from 'moment'
import axios from 'axios'
moment.locale('ko')

//https://ws-pace.tistory.com/105 채팅방

const socket = {
    namespaced : true,
    state(){
        return{
            // alarm var
            alarmState : '',
            recvList: [],

            // chatting var
            id : "",
            text: "",

            textArea_text : '',

            alarmColor : '',
            sendChat : {
                id : 'default',
                text : 'default',
                date : 'default',
                originDate : '',
                sumOriginDate : '',
                // 이미지는 projectList(projects)에서 따로 받아와야한다
                img : '',
            },
            receivedChat : {
                id : 'received',
                text : 'received',
                date : 'received',
                img : '',
            },
            alarmCnt: 0,
            chatCnt : 0,
            receiveChatCnt : 0,
            textAreaCnt : 0,
            s_chatData : {
                roomId: "0",
                isMini: false,
                content: [
                    // {
                    //     id: "kade",
                    //     text: "안대근입니다",
                    //     img: "con1.jpg",
                    //     date: "16:30",
                    // },
                ],
            },
            callDateLineCmt : 0 ,
        }
    },
    mutations : {
        setAlarmColor(state, color){
            state.alarmColor = color
        },
        setSendChatting(state, arr){
            const temp = {
                // id는 꼭 로그인한 id나 idx로 값을 입력해줘야함
                id : arr.id,
                text : arr.text,
                date : moment().format('YYYY-MM-DD')+'T'+ arr.date,
                img : 'con1.jpg',
                originDate : moment().format('YYYY-MM-DD')+' '+ arr.date,
            }
            this.commit('socket/pushToS_chatDataContent', temp)
        },
        resetSendChatValues(state){
            state.sendChat.id = ''
            state.sendChat.text = ''
            state.sendChat.date = ''
            state.sendChat.img = ''
        },
        setReceivedChatting(state,arr){
            // console.log('arr => ',arr)
            state.receivedChat.id = arr.id
            state.receivedChat.text = arr.text
            state.receivedChat.date = arr.date

            const temp = {
                // id는 꼭 로그인한 id나 idx로 값을 입력해줘야함
                id : arr.id,
                text : arr.text,
                date : moment().format('YYYY-MM-DD')+'T'+ arr.date,
                img : 'con1.jpg',
                originDate : moment().format('YYYY-MM-DD')+' '+ arr.date,
            }
            this.commit('socket/pushToS_chatDataContent', temp)
        },
        setTextArea_Text(state, text){
            state.textArea_text = text
        },
        increaseAlarmCnt(state){
            state.alarmCnt++
        },
        increaseChatCnt(state){
            state.chatCnt++
        },
        increaseReceiveChatCnt(state){
            state.receiveChatCnt++
        },
        increaseTextAreaCnt(state){
            state.textAreaCnt++
        },
        increaseCallDateLineCnt(state){
            state.callDateLineCmt++
        },
        setIdValue(state, value){
            state.sendChat.id = value
        },
        setTextValue(state, value){
            state.text = value
        },
        alarm(state,color){
            if(color !== null){
                if(this.stompClient && this.stompClient.connected){
                    const msg = {
                        alarm : color,
                    }
                    this.stompClient.send("/alarm", JSON.stringify(msg), {})
                }
            }// color if
        },// color function end

        // sendMessage(state, {id,text}) {
        sendMessage(state, arr) {

            // store에 값 저장함
            this.commit('socket/send', arr)
            // 소켓에 데이터 전송 후 store 에 저장된 값을 초기화함
            this.dispatch('socket/callAxiosForChatLog', arr)
        }, // sendMessageFunction end

        send(state, arr) {
            if(this.stompClient && this.stompClient.connected) {
                const msg = {
                    id: arr.id,
                    text: arr.text,
                    date : arr.date
                }
                this.stompClient.send("/receive", JSON.stringify(msg), {})
            }
        },// send function end

        sendTextArea(state, text){
            if(this.stompClient && this.stompClient.connected){
                let arr_textAreaText = {
                    textAreaText : '',
                }
                arr_textAreaText.textAreaText = text
                state.textArea_text = text
                this.stompClient.send('/textArea', JSON.stringify(arr_textAreaText), {})
            }
            // this.textArea_text = ''
        },

        pushToS_chatDataContent(state, arr){
            const {originDate, date} = arr

            let sumOriginDate = originDate.split("-")[0] + originDate.split("-")[1] + originDate.split("-")[2].substring(0,2)
            arr.date = moment(date).format('LT')
            arr.sumOriginDate = sumOriginDate
            arr.momentedDate = moment(originDate).format('LL') +' '+ moment(originDate).format('dddd')

            // 렌더링 시 껍데기 데이터에 line true값 추가
            if( state.s_chatData.content.length > 0 ){
                const Index = state.s_chatData.content.length-1
                if(arr.sumOriginDate !== undefined){
                    if(state.s_chatData.content[Index].sumOriginDate !== arr.sumOriginDate){
                        arr.line = true
                    }
                }
            }
            state.s_chatData.content.push(arr)
        },
        // select 쿼리 후에 line true값 추가
        findDifferentOriginDate(state){
            for(let i = 0; i < state.s_chatData.content.length-1; i++){
                if(state.s_chatData.content[i].sumOriginDate !== state.s_chatData.content[i+1].sumOriginDate){
                    state.s_chatData.content[i+1].line = true
                }
            }
        },


    },
    actions : {
        connect(state){
            const serverURL = "http://192.168.0.8:8099/"
            let socket = new SockJS(serverURL)
            this.stompClient = Stomp.over(socket)
            this.stompClient.connect( {}, frame => {
                    this.stompClient.connected = true
                    console.log('store에서의 frame ==>>',frame)

                    this.stompClient.subscribe("/send", res => {
                        console.log("소켓에서 수신한 내용 =>>>>", JSON.parse(res.body))
                        let arr = JSON.parse(res.body)

                        const {alarm, textAreaText, userName, message} = arr

                        if(alarm !== null && alarm !== '' && textAreaText === null){
                            state.alarmState = alarm
                            this.commit('socket/setAlarmColor', alarm)
                            this.commit('socket/increaseAlarmCnt')
                        }else if(userName !== null && userName !== '' && message !== null && message !== '' && textAreaText === null){
                            this.commit('socket/setReceivedChatting',arr)
                            this.commit('socket/increaseReceiveChatCnt')
                        }else if(textAreaText != null){
                            this.commit('socket/setTextArea_Text', textAreaText)
                            this.commit('socket/increaseTextAreaCnt')
                        }
                    })
                },
                error => {
                    console.log(error)
                    this.stompClient.connected = false
                })
        }, // connect function end

        callAxiosForChatLog(state, arr){
            const url = '/socket/insertChatLog'
            const header = null

            //토큰에서 받아오는 memIdx이어야함
            let memIdx
            if(arr.id === 'yunyun'){
                memIdx = 36
            }else if(arr.id ==='zerozero'){
                memIdx = 3
            }

            axios.post( url, header, {
                params : {
                    chatContentIdx : null,
                    // zerochae의 idx가 3임
                    'member.memIdx' : memIdx,
                    'chat.project.prjctIdx' : 1,
                    'chat.member.memIdx' : memIdx,
                    chatCn : arr.text,
                    chatCnDate : moment().format('YYYY-MM-DD')+' '+ arr.date,
                }
            })
                .then( result => {
                    console.log(result)
                })
        },

        callDataOfAllChat(){
            const url = '/socket/selectAllChatLog'
            axios.get( url, {
                params : {
                    'member.memIdx' : 3,
                    'chat.project.prjctIdx' : 1,
                    'chat.member.memIdx' : 3,
                }
            }).then( e => {

                const {data} = e

                for(let i = 0; i < data.length; i++){
                    const arr = {
                        id : data[i].chat.member.memId,
                        text : data[i].chatCn,
                        img : 'con1.jpg',
                        date : data[i].chatCnDate,
                        originDate : data[i].chatCnDate.replace('T', ' '),
                    }
                    this.commit('socket/pushToS_chatDataContent', arr)
                }
                this.commit('socket/findDifferentOriginDate')
            })
        },

        getAllRoomFindByMemIdx(){
            const url = '/socket/getAllRoom'
            axios.post( url, null, {
                params : {
                    memIdx : 3,
                }
            }).then( e => {
                console.log(e)
                console.log(e.data.length)
            })
        },
    },

}

export default socket;