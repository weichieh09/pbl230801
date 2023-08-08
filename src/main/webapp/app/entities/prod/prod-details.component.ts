import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProd } from '@/shared/model/prod.model';
import ProdService from './prod.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProdDetails extends Vue {
  @Inject('prodService') private prodService: () => ProdService;
  @Inject('alertService') private alertService: () => AlertService;

  public prod: IProd = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.prodId) {
        vm.retrieveProd(to.params.prodId);
      }
    });
  }

  public retrieveProd(prodId) {
    this.prodService()
      .find(prodId)
      .then(res => {
        this.prod = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
