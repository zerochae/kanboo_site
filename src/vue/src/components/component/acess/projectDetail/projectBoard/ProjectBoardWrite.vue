<template>
        <editor :isExport="isExport" @exportContent="getContent"/>
        <div id="write-btn">
            <button type="button" @click="insert">등록</button>
        </div>
</template>

<script>
import editor from '../../../global/editor.vue'

export default {

    name: 'App',
    data() {
        return {
            isExport: 0,
        }
    },
    props : {
        step : Number,
        category : String
    },
    components : {
        editor,
    },

    methods : {
        insert(){
            this.isExport++
        },

        exportContent(item) {
            console.log(item)
        },
        
        //게시글 insert
        getContent(e) {
            console.log('아아아아아앙')
            var board = {
                //jwt에서 회원 고유번호 or Token 꺼내와서 담아주기
                idx : "",
                //게시글 내용
                // text : e._data,
                //date는 DB에서 SYSDATE로 처리
                date : '',
                isDelete : "N",
                category : this.category,
                file : e._file,
            }
            console.log(board)
            this.axios.post('',null, { params : { data : board}}).then(e => {
                console.log(e)
            })

        }
    },

}
</script>

<style scoped>
.write-div {
    color: #fff;
    height: 200px;
    
}
#write-btn button {
    color: #fff;
    background-color: coral;
    border-radius: 5px;
}
#write-btn {
    padding-top: 16px;
    color: #fff;
    display: flex;
    justify-content: right;
    margin-bottom: 20px;
    margin-top: -35px;
}



</style>