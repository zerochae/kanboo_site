import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import axios from 'axios'
import store from './store/index'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.bubble.css'

import VueMarkdownEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'

import VMdPreviewHtml from '@kangc/v-md-editor/lib/preview-html'
import '@kangc/v-md-editor/lib/style/preview-html.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import Prism from 'prismjs'

VueMarkdownEditor.use(vuepressTheme, {
  Prism
})


// import Codemirror from 'codemirror-editor-vue3'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';


axios.defaults.baseURL = "http://localhost:8099"

const app = createApp(App)
app.config.globalProperties.axios = axios

app
  .use(router)
  .use(store)
  // .use(Codemirror)
  .use(VMdPreviewHtml)
  .use(VueMarkdownEditor)
  .component("QuillEditor", QuillEditor)
  .mount('#app')
