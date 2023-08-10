import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import TeamService from './team/team.service';
import PlayerService from './player/player.service';
import TeamPlayerService from './team-player/team-player.service';
import EventZService from './event-z/event-z.service';
import EventPlayerService from './event-player/event-player.service';
import MatchZService from './match-z/match-z.service';
import MatchPlayerService from './match-player/match-player.service';
import VwEventResultService from './vw-event-result/vw-event-result.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('teamService') private teamService = () => new TeamService();
  @Provide('playerService') private playerService = () => new PlayerService();
  @Provide('teamPlayerService') private teamPlayerService = () => new TeamPlayerService();
  @Provide('eventZService') private eventZService = () => new EventZService();
  @Provide('eventPlayerService') private eventPlayerService = () => new EventPlayerService();
  @Provide('matchZService') private matchZService = () => new MatchZService();
  @Provide('matchPlayerService') private matchPlayerService = () => new MatchPlayerService();
  @Provide('vwEventResultService') private vwEventResultService = () => new VwEventResultService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
