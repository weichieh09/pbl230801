<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">管理者功能</h3>
        <hr />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg">
          <b-icon icon="BrushFill" />
          <br />
          <h5>總覽</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/team">
          <b-icon icon="PeopleFill" />
          <br />
          <h5>球隊</h5>
        </b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/event-z">
          <b-icon icon="Calendar3WeekFill" />
          <br />
          <h5>賽事</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg">
          <b-icon icon="QuestionCircleFill" />
          <br />
          <h5>關於</h5>
        </b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" to="/">回首頁</b-button>
      </b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script lang="ts" src="./p3.ts"></script>
