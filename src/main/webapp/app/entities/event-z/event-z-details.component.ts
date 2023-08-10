import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEventZ } from '@/shared/model/event-z.model';
import EventZService from './event-z.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EventZDetails extends Vue {
  @Inject('eventZService') private eventZService: () => EventZService;
  @Inject('alertService') private alertService: () => AlertService;

  public eventZ: IEventZ = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eventZId) {
        vm.retrieveEventZ(to.params.eventZId);
      }
    });
  }

  public retrieveEventZ(eventZId) {
    this.eventZService()
      .find(eventZId)
      .then(res => {
        this.eventZ = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
