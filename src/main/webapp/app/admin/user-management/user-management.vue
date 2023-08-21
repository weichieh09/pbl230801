<template>
  <div class="container">
    <!-- <h2>
        <span id="user-management-page-heading" data-cy="userManagementPageHeading">Users</span>

        <div class="d-flex justify-content-end">
          <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isLoading">
            <font-awesome-icon icon="sync" :spin="isLoading"></font-awesome-icon> <span>Refresh List</span>
          </button>
          <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUserCreate' }">
            <button @click="navigate" class="btn btn-primary jh-create-entity">
              <font-awesome-icon icon="plus"></font-awesome-icon> <span>新增</span>
            </button>
          </router-link>
        </div>
      </h2> -->
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
              <!-- <th scope="col" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th> -->
              <th scope="col">
                <h5>帳號</h5>
              </th>
              <!-- <th scope="col" v-on:click="changeOrder('email')">
              <span>Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th> -->
              <th scope="col">
                <h5>狀態</h5>
              </th>
              <!-- <th scope="col"><span>Profiles</span></th> -->
              <!-- <th scope="col" v-on:click="changeOrder('createdDate')">
              <span>Created Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdDate'"></jhi-sort-indicator>
            </th> -->
              <!-- <th scope="col" v-on:click="changeOrder('lastModifiedBy')">
              <span>Last Modified By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedBy'"></jhi-sort-indicator>
            </th> -->
              <!-- <th scope="col" id="modified-date-sort" v-on:click="changeOrder('lastModifiedDate')">
              <span>Last Modified Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedDate'"></jhi-sort-indicator>
            </th> -->
              <th scope="col">
                <h5>操作</h5>
              </th>
            </tr>
          </thead>
          <tbody v-if="users">
            <tr v-for="user in users" :key="user.id" :id="user.login">
              <!-- <td>
              <router-link :to="{ name: 'JhiUserView', params: { userId: user.login } }">{{ user.id }}</router-link>
            </td> -->
              <td>
                <h5>{{ user.login }}</h5>
              </td>
              <!-- <td class="jhi-user-email">{{ user.email }}</td> -->
              <td>
                <button class="btn btn-danger btn-sm deactivated" v-if="!user.activated">停用</button>
                <button class="btn btn-success btn-sm" v-if="user.activated" :disabled="username === user.login">啟用</button>
              </td>

              <!-- <td>
              <div v-for="authority of user.authorities" :key="authority">
                <span class="badge badge-info">{{ authority }}</span>
              </div>
            </td> -->
              <!-- <td>{{ user.createdDate | formatDate }}</td> -->
              <!-- <td>{{ user.lastModifiedBy }}</td> -->
              <!-- <td>{{ user.lastModifiedDate | formatDate }}</td> -->
              <td>
                <!-- <router-link :to="{ name: 'JhiUserView', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link> -->
                <!-- <router-link :to="{ name: 'JhiUserEdit', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link> -->
                <!-- <b-button v-on:click="prepareRemove(user)" variant="danger" class="btn btn-sm delete" :disabled="username === user.login">
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button> -->

                <router-link :to="{ name: 'JhiUserEdit', params: { userId: user.login } }" custom v-slot="{ navigate }">
                  <b-button style="margin: 1px" variant="info" @click="navigate">編輯</b-button> </router-link
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
