/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { GrupoDetailComponent } from 'app/entities/grupo/grupo-detail.component';
import { Grupo } from 'app/shared/model/grupo.model';

describe('Component Tests', () => {
    describe('Grupo Management Detail Component', () => {
        let comp: GrupoDetailComponent;
        let fixture: ComponentFixture<GrupoDetailComponent>;
        const route = ({ data: of({ grupo: new Grupo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [GrupoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GrupoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.grupo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
