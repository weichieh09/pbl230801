<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h3 style="text-align: center">用戶管理</h3>
        <hr />
      </div>
    </div>
    <div class="row">
      <div class="col">
        <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUserCreate' }">
          <b-button block variant="primary" @click="navigate" size="lg">新增用戶</b-button>
        </router-link>
        <br />
      </div>
    </div>
    <div class="row" v-if="users">
      <div class="col">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">
                <h5>帳號</h5>
              </th>
              <th scope="col">
                <h5>狀態</h5>
              </th>
              <th scope="col">
                <h5>操作</h5>
              </th>
            </tr>
          </thead>
          <tbody v-if="users">
            <tr v-for="user in users" :key="user.id" :id="user.login">
              <td>
                <h5>{{ user.login }}</h5>
              </td>
              <td>
                <button class="btn btn-danger btn-sm deactivated" v-if="!user.activated" v-on:click="setActive(user, true)">停用</button>
                <button
                  class="btn btn-success btn-sm"
                  v-if="user.activated"
                  :disabled="username === user.login"
                  v-on:click="setActive(user, false)"
                >
                  啟用
                </button>
              </td>
              <td>
                <router-link :to="{ name: 'JhiUserEdit', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <b-button style="margin: 1px" variant="info" @click="navigate" :disabled="username === user.login"
                    >編輯</b-button
                  > </router-link
                ><br />
                <b-button style="margin: 1px" variant="danger" @click="prepareRemove(user)" :disabled="username === user.login"
                  >刪除</b-button
                ><br />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)" align="fill" />
      </div>
    </div>

    <div class="row">
      <div class="col">
        <hr />
        <b-button block variant="outline-primary" size="lg" @click="$router.go(-1)">返回</b-button>
        <br />
      </div>
    </div>

    <div class="row">
      <div class="col">
        <b-modal ref="removeUser" id="removeUser" @ok="deleteUser()" title="刪除用戶">
          <div class="row">
            <div class="col">確定刪除此用戶?</div>
          </div>
          <div slot="modal-footer">
            <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">取消</button>
            <button type="button" class="btn btn-danger" id="confirm-delete-user" v-on:click="deleteUser()">刪除</button>
          </div>
        </b-modal>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management.component.ts"></script>
