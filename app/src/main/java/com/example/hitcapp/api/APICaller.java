package com.example.hitcapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hitcapp.models.Category;
import com.example.hitcapp.models.Product;
import com.example.hitcapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APICaller {
    private static final String TAG = "APICaller";
    private static final String BASE_URL = "http://10.180.6.181:8080";
    private final RequestQueue requestQueue;
    private static APICaller instance;

    public interface OnApiResponse {
        void onSuccess(String response);
        void onError(String error);
    }

    public interface OnRawResponse {
        void onSuccess(String response);
        void onError(String error);
    }

    public interface OnCategoryLoaded {
        void onLoaded(List<Category> categoryList);
    }

    public interface OnProductLoaded {
        void onLoaded(List<Product> productList);
    }
    
    public interface OnUserLoaded { void onSuccess(User user); void onError(String error); }

    private APICaller(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    
    public static synchronized APICaller getInstance(Context context) {
        if (instance == null) instance = new APICaller(context);
        return instance;
    }

    private Product parseProduct(JSONObject obj) throws JSONException {
        int id             = obj.optInt("id", -1);
        String name        = obj.optString("name", "");
        String description = obj.optString("description", "");
        double price       = obj.optDouble("price", 0.0);
        int qty            = obj.optInt("qty", 0);

        String categoryName = "";
        JSONObject categoryObj = obj.optJSONObject("category");
        if (categoryObj != null) {
            categoryName = categoryObj.optString("category", "");
        }

        String imageBase64 = "";
        JSONObject imageObj = obj.optJSONObject("image");
        if (imageObj != null) {
            imageBase64 = imageObj.optString("image", "");
        }

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQty(qty);
        product.setCategoryName(categoryName);
        product.setImageBase64(imageBase64);

        return product;
    }

    private Category parseCategory(JSONObject obj) throws JSONException {
        int id             = obj.optInt("id", -1);
        String categoryName = obj.optString("category", "");
        String imageBase64 = "";
        JSONObject imageObj = obj.optJSONObject("image");
        if (imageObj != null) {
            imageBase64 = imageObj.optString("image", "");
        }
        Category c = new Category();
        c.setId(id);
        c.setName(categoryName);
        c.setImageBase64(imageBase64);
        return c;
    }

    public void getItems(String endpoint, Map<String, String> params, OnRawResponse callback) {
        String fullUrl = buildUrlWithParams(BASE_URL + endpoint, params);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                fullUrl,
                response -> callback.onSuccess(response),
                error -> callback.onError(error.toString())
        );
        requestQueue.add(request);
    }

    private String buildUrlWithParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) return baseUrl;
        StringBuilder url = new StringBuilder(baseUrl).append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url.setLength(url.length() - 1);
        return url.toString();
    }

    public void getCategory(OnCategoryLoaded callback) {
        getItems("/ProductCategory/GetAll", null, new OnRawResponse() {
            @Override
            public void onSuccess(String response) {
                List<Category> categoryList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        categoryList.add(parseCategory(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) { Log.e(TAG, e.getMessage()); }
                callback.onLoaded(categoryList);
            }
            @Override public void onError(String error) { callback.onLoaded(new ArrayList<>()); }
        });
    }

    public void getProducts(OnProductLoaded callback) {
        getItems("/products/getall", null, new OnRawResponse() {
            @Override
            public void onSuccess(String response) {
                List<Product> productList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        productList.add(parseProduct(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) { Log.e(TAG, e.getMessage()); }
                callback.onLoaded(productList);
            }
            @Override public void onError(String error) { callback.onLoaded(new ArrayList<>()); }
        });
    }

    public void searchProducts(String query, OnProductLoaded callback) {
        if (query == null || query.trim().isEmpty()) {
            getProducts(callback);
            return;
        }
        getItems("/products/getall/" + query.trim(), null, new OnRawResponse() {
            @Override
            public void onSuccess(String response) {
                List<Product> productList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        productList.add(parseProduct(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) { Log.e(TAG, e.getMessage()); }
                callback.onLoaded(productList);
            }
            @Override public void onError(String error) { callback.onLoaded(new ArrayList<>()); }
        });
    }

    public void register(User userData, OnApiResponse callback) {
        String url = BASE_URL + "/user/register";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", userData.getFirstName());
            jsonObject.put("lastName", userData.getLastName());
            jsonObject.put("userName", userData.getUserName());
            jsonObject.put("emailId", userData.getEmailId());
            jsonObject.put("password", userData.getPassword());
            jsonObject.put("active", true);
        } catch (JSONException e) { callback.onError(e.getMessage()); return; }
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> callback.onSuccess("Đăng ký thành công"),
                error -> callback.onError("Lỗi đăng ký")
        );
        requestQueue.add(request);
    }

    public void login(String emailId, String password, OnApiResponse callback) {
        String url = BASE_URL + "/user/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailId", emailId);
            jsonObject.put("password", password);
        } catch (JSONException e) { callback.onError(e.getMessage()); return; }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> callback.onSuccess(response.toString()),
                error -> callback.onError("Sai tài khoản hoặc mật khẩu")
        );
        requestQueue.add(request);
    }

    public void checkoutSimple(int userId, String userName, double totalPrice, int totalQty, OnApiResponse callback) {
        String url = BASE_URL + "/cart/add";
        JSONObject body = new JSONObject();
        try {
            body.put("userId", userId);
            body.put("userName", userName);
            body.put("totalPrice", (int)totalPrice);
            body.put("totalQuantity", totalQty);
        } catch (JSONException e) { callback.onError("Lỗi tạo dữ liệu"); return; }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> callback.onSuccess("Đặt hàng thành công"),
                error -> callback.onError("Lỗi đặt hàng")
        );
        requestQueue.add(request);
    }
    
    public void getUserById(int userId, final OnUserLoaded callback) {
        String url = BASE_URL + "/user/get/" + userId;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                resp -> {
                    try {
                        JSONObject obj = new JSONObject(resp);
                        User u = new User();
                        u.setId(obj.optInt("id"));
                        u.setFirstName(obj.optString("firstName"));
                        u.setLastName(obj.optString("lastName"));
                        u.setUserName(obj.optString("userName"));
                        u.setEmailId(obj.optString("emailId"));
                        callback.onSuccess(u);
                    } catch (JSONException e) { callback.onError("Lỗi parse"); }
                },
                err -> callback.onError("Lỗi server")
        );
        requestQueue.add(req);
    }

    public void getProductById(int productId, final OnProductLoaded callback) {
        String url = BASE_URL + "/products/get/" + productId;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                resp -> {
                    try {
                        List<Product> list = new ArrayList<>();
                        list.add(parseProduct(new JSONObject(resp)));
                        callback.onLoaded(list);
                    } catch (JSONException e) { callback.onLoaded(new ArrayList<>()); }
                },
                err -> callback.onLoaded(new ArrayList<>())
        );
        requestQueue.add(req);
    }
    public void changePassword(int userId, String oldPass, String newPass, final OnApiResponse callback) {
        String url = BASE_URL + "/user/changePassword?id=" + userId + "&oldPassword=" + oldPass + "&newPassword=" + newPass;
        StringRequest req = new StringRequest(Request.Method.POST, url,
                resp -> callback.onSuccess("Đổi mật khẩu thành công"),
                err -> callback.onError("Lỗi: " + err.toString())
        );
        requestQueue.add(req);
    }
    public void updateUser(User user, final OnApiResponse callback) {
        String url = BASE_URL + "/user/update";
        JSONObject body = new JSONObject();
        try {
            body.put("id", user.getId());
            body.put("firstName", user.getFirstName());
            body.put("lastName", user.getLastName());
            body.put("userName", user.getUserName());
            body.put("emailId", user.getEmailId());
            body.put("password", "123456"); 
            body.put("active", true);
        } catch (JSONException e) { callback.onError("Lỗi tạo dữ liệu"); return; }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> callback.onSuccess("Cập nhật thành công"),
                error -> callback.onError("Lỗi cập nhật")
        );
        requestQueue.add(request);
    }
}
