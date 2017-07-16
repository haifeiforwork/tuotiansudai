# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='CurrentAccount',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25, unique=True)),
                ('balance', models.PositiveIntegerField(default=0)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now_add=True)),
            ],
            options={
                'db_table': 'current_account',
            },
        ),
        migrations.CreateModel(
            name='CurrentBill',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25)),
                ('bill_date', models.DateTimeField()),
                ('bill_type', models.CharField(choices=[(b'DEPOSIT', '\u4e70\u5165'), (b'WITHDRAW', '\u8d4e\u56de'), (b'INTEREST', '\u7ed3\u606f')], max_length=10)),
                ('amount', models.PositiveIntegerField()),
                ('balance', models.PositiveIntegerField()),
                ('order_id', models.IntegerField()),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now_add=True)),
                ('current_account', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='current_bills', related_query_name='current_bill', to='current_rest.CurrentAccount')),
            ],
            options={
                'db_table': 'current_bill',
            },
        ),
        migrations.CreateModel(
            name='CurrentDeposit',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25)),
                ('amount', models.PositiveIntegerField()),
                ('status', models.CharField(choices=[(b'WAITING_PAY', '\u5f85\u652f\u4ed8'), (b'SUCCESS', '\u4e70\u5165\u6210\u529f'), (b'FAIL', '\u4e70\u5165\u5931\u8d25')], default=b'WAITING_PAY', max_length=20)),
                ('source', models.CharField(choices=[(b'WEB', '\u7f51\u7ad9'), (b'WE_CHAT', '\u5fae\u4fe1'), (b'IOS', 'iOS'), (b'ANDROID', 'Android')], default=b'WEB', max_length=10)),
                ('no_password', models.BooleanField(default=False)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now_add=True)),
                ('current_account', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='current_deposits', related_query_name='current_deposit', to='current_rest.CurrentAccount')),
            ],
            options={
                'db_table': 'current_deposit',
            },
        ),
        migrations.CreateModel(
            name='CurrentWithdraw',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('account_id', models.IntegerField()),
                ('amount', models.IntegerField()),
                ('status', models.CharField(
                    choices=[(b'WAITING', '\u5f85\u5ba1\u6838'), (b'APPROVED', '\u5df2\u5ba1\u6838'),
                             (b'DENIED', '\u5df2\u9a73\u56de')], default=b'WAITING', max_length=20)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('approve_time', models.DateTimeField(auto_now=True)),
                ('approver', models.CharField(max_length=30)),
            ],
            options={
                'db_table': 'current_withdraw',
            },
        ),
    ]
