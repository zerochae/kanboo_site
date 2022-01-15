package com.kanboo.www.util;///*
//package com.kanboo.www.util;
//
//import com.google.gson.JsonObject;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class SendSMS {
//
//
//    class GroupModel {
//        private ArrayList<JsonObject> log;
//        private JsonObject agent;
//        private JsonObject count;
//        private String accountId;
//        private String apiVersion;
//        private String groupId;
//        private String dateCreated;
//        private String dateUpdated;
//        private String _id;
//        private String status;
//
//        public ArrayList<JsonObject> getLog() {
//            return log;
//        }
//
//        public JsonObject getAgent() {
//            return agent;
//        }
//
//        public JsonObject getCount() {
//            return count;
//        }
//
//        public String getAccountId() {
//            return accountId;
//        }
//
//        public String getApiVersion() {
//            return apiVersion;
//        }
//
//        public String getGroupId() {
//            return groupId;
//        }
//
//        public String getDateCreated() {
//            return dateCreated;
//        }
//
//        public String getDateUpdated() {
//            return dateUpdated;
//        }
//
//        public String get_id() {
//            return _id;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//    }
//
//    public static void send(String receiver, String Caller, String token) {
//
//        JsonObject msg = new JsonObject();
//
//        msg.addProperty("to", receiver);
//        msg.addProperty("from", Caller);
//        msg.addProperty("text", "안녕하세요 당신의 친구 깐부입니다. 당신의 token은 " + token + " 입니다.");
//
//        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), msg);
//
//        api.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
//                // 성공 시 200이 출력됩니다.
//                if (response.isSuccessful()) {
//                    System.out.println("statusCode : " + response.code());
//                    GroupModel body = response.body();
//                    System.out.println("groupId : " + body.getGroupId());
//                    System.out.println("status: " + body.getStatus());
//                    System.out.println("count: " + body.getCount().toString());
//                } else {
//                    try {
//                        System.out.println(response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GroupModel> call, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
//
//    }
//}
//*/
