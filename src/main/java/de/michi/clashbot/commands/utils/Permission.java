package de.michi.clashbot.commands.utils;

import de.michi.clashbot.ClashBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public enum Permission {


    EVERYONE(1),
    MEMBER(2),
    ELDER(3),
    COLEADER(4),
    LEADER(5);

    private int amount;

    Permission(int amount) {
        this.amount = amount;
    }

    public static boolean hasPerm(Permission perm, Permission needed) {
        return perm.amount >= needed.amount;
    }

    public static Permission getPermission(String userid) {
        Permission perm = EVERYONE;
        Member user = ClashBot.jda.getGuildById("393823613557669888").getMember(ClashBot.jda.getUserById(userid));
        for (Role role : user.getRoles()) {
            switch (role.getId()) {
                case "393842652509896725":
                    if (perm.amount <= MEMBER.amount)
                        perm = MEMBER;
                    break;
                case "393833380510302208":
                    if (perm.amount <= ELDER.amount)
                        perm = ELDER;
                    break;
                case "393833691694235650":
                    if (perm.amount <= COLEADER.amount)
                        perm = COLEADER;
                    break;
                case "393833915598503936":
                    if (perm.amount <= LEADER.amount)
                        perm = LEADER;
                    break;
                default:
                    break;
            }
        }
        return perm;
    }
}
