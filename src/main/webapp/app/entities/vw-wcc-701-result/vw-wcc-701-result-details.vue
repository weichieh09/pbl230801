<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vwWcc701Result">
        <h2 class="jh-entity-heading" data-cy="vwWcc701ResultDetailsHeading"><span>VwWcc701Result</span> {{ vwWcc701Result.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>E Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.eId }}</span>
          </dd>
          <dt>
            <span>T Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.tId }}</span>
          </dd>
          <dt>
            <span>Evnt Nm</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.evntNm }}</span>
          </dd>
          <dt>
            <span>Evnt Dt</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.evntDt | formatDate }}</span>
          </dd>
          <dt>
            <span>Venue</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.venue }}</span>
          </dd>
          <dt>
            <span>M Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.mId }}</span>
          </dd>
          <dt>
            <span>Mtch End Time</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.mtchEndTime | formatDate }}</span>
          </dd>
          <dt>
            <span>W Plyr 1 Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr1Id }}</span>
          </dd>
          <dt>
            <span>W Plyr 1 Lvl</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr1Lvl }}</span>
          </dd>
          <dt>
            <span>W Plyr 1 Nm</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr1Nm }}</span>
          </dd>
          <dt>
            <span>W Plyr 2 Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr2Id }}</span>
          </dd>
          <dt>
            <span>W Plyr 2 Lvl</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr2Lvl }}</span>
          </dd>
          <dt>
            <span>W Plyr 2 Nm</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wPlyr2Nm }}</span>
          </dd>
          <dt>
            <span>Vs</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.vs }}</span>
          </dd>
          <dt>
            <span>L Plyr 1 Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr1Id }}</span>
          </dd>
          <dt>
            <span>L Plyr 1 Lvl</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr1Lvl }}</span>
          </dd>
          <dt>
            <span>L Plyr 1 Nm</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr1Nm }}</span>
          </dd>
          <dt>
            <span>L Plyr 2 Id</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr2Id }}</span>
          </dd>
          <dt>
            <span>L Plyr 2 Lvl</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr2Lvl }}</span>
          </dd>
          <dt>
            <span>L Plyr 2 Nm</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lPlyr2Nm }}</span>
          </dd>
          <dt>
            <span>W Scr</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.wScr }}</span>
          </dd>
          <dt>
            <span>L Scr</span>
          </dt>
          <dd>
            <span>{{ vwWcc701Result.lScr }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vw-wcc-701-result-details.component.ts"></script>
