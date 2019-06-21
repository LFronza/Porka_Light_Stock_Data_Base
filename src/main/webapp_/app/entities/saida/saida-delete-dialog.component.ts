import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';

@Component({
    selector: 'jhi-saida-delete-dialog',
    templateUrl: './saida-delete-dialog.component.html'
})
export class SaidaDeleteDialogComponent {
    saida: ISaida;

    constructor(protected saidaService: SaidaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saidaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saidaListModification',
                content: 'Deleted an saida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-saida-delete-popup',
    template: ''
})
export class SaidaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saida }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.saida = saida;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/saida', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/saida', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
