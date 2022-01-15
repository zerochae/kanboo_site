<template>
  <div class="Repo">
    <h1>Project Repository</h1>
    <div class="nodeTree">
      <h1>Document List</h1>
      <div class="repoContent">
        <Tree
            :search-text="searchText"
            :use-icon="true"
            @nodeExpanded="onUpdate"
            :nodes="data"
        />
      </div>
    </div>
  </div>
</template>

<script>
import {mapMutations} from 'vuex'
import {mapActions} from 'vuex'
import {ref} from 'vue';
import Tree from 'vue3-tree'
import "vue3-tree/dist/style.css";
import moment from "moment" // eslint-disable-line no-unused-vars


const key = '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';

export default {
  components: {
    Tree,
  },
  data() {
    return {
      data: ref([]),
      searchText: ref(''),
      encodedData: '',
      errorFileNames : ['package-lock.json', '.DS_Store'],
    }
  },
  methods: {
    ...mapMutations({
      setDecodeData: 'git/setDecodeData',
      setSelectedFileName: 'git/setSelectedFileName',
      setIssueDate: 'git/setIssueDate',
      setSelectedFileSize : 'git/setSelectedFileSize'
    }),
    ...mapActions({
      getRepoList: 'git/getRepoList',
    }),

    onUpdate(e) {
      if (e.type === 'file') {
        this.sendContent(e)
        return
      }

      this.axios.get(`${e.url}`, {
        headers: {
          Authorization: `token ${key}`
        }
      })
          .then(res => {
            for (let i of res.data) {

              if (i.name === 'package-lock.json') {
                continue
              }
              if (i.name === '.DS_Store') {
                continue
              }

              const a = {
                idx: i.sha,
                label: i.name,
                type: i.type,
                url: i.url,
                nodes: [],
                content: null,
              }
              if (i.type === 'file') {
                a.nodes = null
                a.content = i.content
              }

              e.nodes.push(a)
            }
          })
    },

    filterErrorFiles(file){
      //비동기라서 작동이 잘안됨
      let flag = false;
      for(let i = 0; i < this.errorFileNames.length; i++){
        console.log(this.errorFileNames[i])
        if(this.errorFileNames[i] === file){
          return flag
        }
      }
    },

    sendContent(e) {
      this.axios.get(`${e.url}`, {
        headers: {
          Authorization: `token ${key}`
        }
      })
          .then(res => {
            this.encodedData = res.data.content
            this.decodeData()
            this.setSelectedFileName(res.data.name)
            this.setSelectedFileSize(res.data.size)
          })
    },
    getFileList() {
      this.axios.get('https://api.github.com/repos/Juwon-Yun/kanboo_my_work/contents', {
        headers: {
          Authorization: `token ${key}`
        }
      })
          .then(res => {
            for (let i of res.data) {

              // 403 error 방지
              if(i.name === 'package-lock.json') continue
              if(i.name === '.DS_Store') continue

              const array = {
                idx: i.sha,
                label: i.name,
                type: i.type,
                url: i.url,
                nodes: [],
                content: null,
              }
              if (i.type === 'file') {
                array.nodes = null
                array.content = i.content
              }
              this.data.push(array)
            }// for i of
          })
    },
    getIssueList(){
      const url = '/gitAndIssue/getAllList'

      this.axios.get( url, {
        params : {
          prjctIdx : 1,
        }
      })
          .then( (r)=>{
            for(let i = 0; i < r.data.length; i++){
              r.data[i].issueDate = r.data[i].issueDate.replace('T', ' ')
            }
            this.setIssueDate(r.data)
          })
    },

    decodeData() {
      this.setDecodeData(decodeURIComponent(escape(window.atob(this.encodedData))))
    },
  },

  mounted() {
    this.getFileList();
    this.getIssueList();
  },
}

</script>

<style scoped>
.Repo > h1:nth-child(1) {
  margin-bottom: 3px;
  color: #eee;
  font-size: 21px;
}

.nodeTree {
  height: 21vh;
  background-color: #2C2F3B;
  overflow: scroll;
  -ms-overflow-style: none;
  scrollbar-width: none;
  border-radius: 5px;
}

.nodeTree::-webkit-scrollbar {
  display: none;
}

.nodeTree > h1 {
  padding-bottom: 5px;
  color: #eee;
}

.repoContent {
  color: #eee;
}

.tree-row-item {
  padding: 0;
}

.tree-list {
  gap: 5px;
}
</style>