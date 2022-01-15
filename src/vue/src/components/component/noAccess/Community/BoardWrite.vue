<template>
        <editor :isExport="isExport" @exportContent="getContent"/>
        <div id="write-btn">
            <button type="button" @click="insert()">등록</button>
        </div>
</template>

<script>
import editor from '../../global/editor.vue'
import {mapMutations, mapState} from "vuex";

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
    computed: {
      ...mapState({
          boardList : state => state.community.boardList,
          isOpen : state => state.community.isOpen,
      })
    },

    methods : {
        ...mapMutations({
          unshiftToBoardList : 'community/unshiftToBoardList',
          changeWriteIsOpen : 'community/changeWriteIsOpen',
          changeUpdateCheck : 'community/changeUpdateCheck',
          setArticlesOnView: 'community/setArticlesOnView'
        }),

        insert(){
            this.isExport++
        },

        exportContent(item) {
            console.log(item)
        },

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
            console.log('안뇽이냐??')
            let fileAt = ''
            let formData = new FormData()
            let boardIdxToInsertFile = 0

            if(e._file !== '' ){
              fileAt = 'Y'
            } else {
              fileAt = 'N'
            }
            this.axios({
                method: 'post',
                url: '/insertBoard',
                data: {
                  boardCn: e._data,
                  boardDate: '',
                  delAt: "N",
                  codeDetailIdx: this.category,
                  totalComments: 0,
                  totalLikes: 0,
                  fileAt: fileAt,
                  token: "#1921"
                }
              }).then(ele =>{
                boardIdxToInsertFile = ele.data.boardIdx
                this.changeUpdateCheck()
                this.changeWriteIsOpen()


                if(e._file !== ''){
                  this.makeFormData(e, formData, boardIdxToInsertFile)
                }

                if(e._file !== ''){
                  this.axios.post("/insertFile", formData,
                      { headers: { 'Content-Type': 'multipart/form-data' } })
                                .then(e => {
                                  ele.data.boardFileDTO = e.data
                                  this.pushBoardList(ele.data)
                                })
                }

                if(e._file === '') {
                  this.pushBoardList(ele.data)
                }
            })
        },
        pushBoardList(data) {
          this.unshiftToBoardList(data)
        },

        makeFormData(ele, obj, boardIdxToInsertFile) {
          const fileSize = this.convertFileSize(ele._file.size)
          const fileNameAndExtension = ele._file.name.lastIndexOf('.')
          const fileName = ele._file.name
          console.log(fileName)
          const extension = ele._file.name.substring( fileNameAndExtension, ele._file.name.length)

          obj.append('file', ele._file)
          obj.append('fileSize', fileSize)
          obj.append('fileName', fileName)
          obj.append('extensionName', extension)
          obj.append('boardIdxToInsertFile', boardIdxToInsertFile)
          obj.append('category', this.category)
          obj.append('checkInsertOrUpdate', "insert")
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
}



</style>