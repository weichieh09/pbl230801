<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError">
          <strong>登入失敗！</strong><br />請檢查您的輸入並重試。
        </b-alert>
      </div>
      <div class="col-md-8">
        <b-form role="form" v-on:submit.prevent="doLogin()">
          <b-form-group label="帳號" label-for="username">
            <b-form-input id="username" type="text" name="username" autofocus v-model="login" data-cy="username"> </b-form-input>
          </b-form-group>
          <b-form-group label="密碼" label-for="password">
            <b-form-input id="password" type="password" name="password" v-model.trim="name" v-model="password" data-cy="password">
            </b-form-input>
          </b-form-group>
          <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>
            <span>記住我</span>
          </b-form-checkbox>
          <div>
            <br />
            <b-button data-cy="submit" type="submit" variant="primary">登入</b-button>
          </div>
        </b-form>
        <p></p>
        <!-- <div>
          <b-alert show variant="warning">
            <b-link :to="'/account/reset/request'" class="alert-link" data-cy="forgetYourPasswordSelector"
              >Did you forget your password?</b-link
            >
          </b-alert>
        </div>
        <div>
          <b-alert show variant="warning">
            <span>You don't have an account yet?</span>
            <b-link :to="'/register'" class="alert-link">Register a new account</b-link>
          </b-alert>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
