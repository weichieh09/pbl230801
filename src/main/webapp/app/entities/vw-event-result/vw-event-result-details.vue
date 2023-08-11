<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vwEventResult">
        <h2 class="jh-entity-heading" data-cy="vwEventResultDetailsHeading"><span>VwEventResult</span> {{ vwEventResult.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>E Id</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.eId }}</span>
          </dd>
          <dt>
            <span>P Id</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.pId }}</span>
          </dd>
          <dt>
            <span>M Id</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.mId }}</span>
          </dd>
          <dt>
            <span>Win Fg</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.winFg }}</span>
          </dd>
          <dt>
            <span>Plyr Nm</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.plyrNm }}</span>
          </dd>
          <dt>
            <span>Plyr Lvl</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.plyrLvl }}</span>
          </dd>
          <dt>
            <span>Mtch End Time</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.mtchEndTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Tot Matchs</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.totMatchs }}</span>
          </dd>
          <dt>
            <span>Tot Wins</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.totWins }}</span>
          </dd>
          <dt>
            <span>Acml Wins</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.acmlWins }}</span>
          </dd>
          <dt>
            <span>Chk Fg</span>
          </dt>
          <dd>
            <span>{{ vwEventResult.chkFg }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vw-event-result-details.component.ts"></script>
