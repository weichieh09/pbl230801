<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group prepend="球隊">
          <b-form-input value="運動家羽球隊" />
        </b-input-group>
        <br />
        <b-input-group prepend="日期">
          <b-form-input type="date" value="2020-01-01" />
        </b-input-group>
        <br />
        <b-input-group prepend="場地">
          <b-form-input value="台藝大" />
        </b-input-group>
        <br />
        <b-input-group prepend="賽事">
          <b-form-input value="爭分奪勝搶水果" />
        </b-input-group>
        <br />
      </b-col>
    </b-row>

    <b-row>
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h4 class="text-success">勝方</h4>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
              <th scope="col">分數</th>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>12</td>
              <td>Mark</td>
              <td>3</td>
              <td>12</td>
              <td>Mark</td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h4 class="text-danger">敗方</h4>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
              <th scope="col">分數</th>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>12</td>
              <td>Mark</td>
              <td>3</td>
              <td>12</td>
              <td>Mark</td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <br />
    </b-row>

    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <!-- <b-button variant="outline-primary" size="lg">確認登入</b-button> -->
        <b-button block variant="outline-primary" size="lg">確認登入</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p2.ts"></script>
